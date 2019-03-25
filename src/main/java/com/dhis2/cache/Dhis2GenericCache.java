package com.dhis2.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class Dhis2GenericCache<T> {
  
  private Cache<String, T> cache;
  
  public Dhis2GenericCache() {
    
    // I hope that builds a cache that never expires neither on time nor on size
    cache = Caffeine.newBuilder().build();    
  }
  
  public Collection<T> all() {
    return cache.asMap().values();
  }
  
  public Optional<T> findById(String id) {
    T g = cache.getIfPresent(id);
    if (g != null) {
      return Optional.of(g);
    }
    return Optional.empty();
  }
    
  public void clear() {
    cache.invalidateAll();
  }
  
  public void putAll(Map<String, T> map) {
    cache.putAll(map);
  }
}
