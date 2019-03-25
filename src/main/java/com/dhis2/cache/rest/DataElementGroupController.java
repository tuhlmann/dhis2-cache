package com.dhis2.cache.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dhis2.cache.DataElementGroupRepository;
import com.dhis2.cache.Dhis2CacheManager;
import com.dhis2.cache.MetaFetcher;
import com.dhis2.cache.data.DataElementGroup;
import com.dhis2.cache.data.DataElementGroups;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
class DataElementGroupController {

  private final DataElementGroupRepository repository;
  
  private final Dhis2CacheManager cacheManager;

  DataElementGroupController(DataElementGroupRepository repository, Dhis2CacheManager cacheManager) {
    this.repository = repository;
    this.cacheManager = cacheManager;
  }

  @GetMapping(path = "/dataElementGroups")
  DataElementGroups<DataElementGroup> all() {
//    DataElementGroups<DataElementGroup> groups = new DataElementGroups<>();
//    repository.findAll().iterator().forEachRemaining(groups::add);

    return new DataElementGroups<DataElementGroup>(cacheManager.getGroupCache().asMap().values());
  }

  @GetMapping(path = "/dataElementGroups/{id}")
  DataElementGroup one(
    @PathVariable String id
  ) {
    return repository.findByGroupId(id)
      .orElseThrow(() -> new DataElementGroupNotFoundException(id));
  }

}