package com.dhis2.cache;

import org.springframework.stereotype.Service;

import com.dhis2.cache.data.DataElement;
import com.dhis2.cache.data.DataElementGroup;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Service
public class Dhis2CacheManager {
  
  private Cache<String, DataElementGroup> groupCache;
  private Cache<String, DataElement> elementCache;
  
  public Dhis2CacheManager() {
    
    // I hope that builds a cache that never expires neither on time nor on size
    groupCache = Caffeine.newBuilder().build();    
    elementCache = Caffeine.newBuilder().build();    
  }
  
  public Cache<String, DataElementGroup> getGroupCache() {
    return groupCache;
  }

  public Cache<String, DataElement> getElementCache() {
    return elementCache;
  }
  
}
