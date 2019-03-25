package com.dhis2.cache;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dhis2.cache.data.DataElementService;
import com.dhis2.cache.json.DataElementGroups;
import com.dhis2.cache.json.DataElements;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MetaFetcher {
  
  @Autowired
  private Environment env;
  
  @Autowired
  private DataElementGroupRepository elementGroupRepository;
  
  @Autowired
  private DataElementService dataElementService;

  @Autowired
  private DataElementRepository elementRepository;
  
  private final Dhis2CacheManager cacheManager;

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  private final RestTemplate restTemplate = new RestTemplate();
  
  MetaFetcher(Dhis2CacheManager cacheManager) {
    this.cacheManager = cacheManager;
    restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "district"));    
  }

  private String getDataElementGroupsUrl(String host) {
    return String.format("%s/api/29/dataElementGroups.json?paging=false&fields=id,displayName,dataElements", host);
  }
  
  private String getDataElementsUrl(String host) {
    return String.format("%s/api/29/dataElements.json?paging=false&fields=id,displayName,dataElementGroups", host);
  }
  
  // TODO: add a transaction
  private void storeDataElementGroups(DataElementGroups dataElementGroups) {
//    elementGroupRepository.deleteAll();    
//    elementGroupRepository.saveAll(dataElementService.genDataElementGroups(dataElementGroups));
    
    groups = dataElementService.genDataElementGroups(dataElementGroups);
    cacheManager.getGroupCache().invalidateAll();
    cacheManager.getGroupCache().putAll(dataElementGroups.getAsMap());
  }
  
  // TODO: add a transaction
  private void storeDataElements(DataElements dataElements) {
    elementRepository.deleteAll();
    elementRepository.saveAll(dataElementService.genDataElements(dataElements));
  }
  
  @Scheduled(fixedDelayString = "${metaFetcher.fetchEachMs}", initialDelay = 2000)
  /**
   * Fetching meta data.
   * In a real app each endpoint should have its own thread so they could fetch and persist in parallel
   */
  public void fetchMetaData() {
    log.info("Fetching data element groups now: ", dateFormat.format(new Date()));
    DataElementGroups dataElementGroups = restTemplate.getForObject(getDataElementGroupsUrl(env.getProperty("dhis2.url")), DataElementGroups.class);    
    log.info("DataElementGroups size: " + dataElementGroups.getDataElementGroups().size());
    log.info("First data group has elements: " + dataElementGroups.getDataElementGroups().get(0).getDataElements().size());

    log.info("Fetching data elements now: ", dateFormat.format(new Date()));
    DataElements dataElements = restTemplate.getForObject(getDataElementsUrl(env.getProperty("dhis2.url")), DataElements.class);    
    log.info("Data Elements size: " + dataElements.getDataElements().size());
    
    if (dataElementGroups != null && dataElementGroups.getDataElementGroups().size() > 0) {
      
      storeDataElementGroups(dataElementGroups);
    }
    
    if (dataElements != null && dataElements.getDataElements().size() > 0) {
      
      storeDataElements(dataElements);
    }
    
  }
}