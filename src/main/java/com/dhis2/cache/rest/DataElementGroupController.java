package com.dhis2.cache.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dhis2.cache.Dhis2ElementGroupCache;
import com.dhis2.cache.data.DataElementGroup;
import com.dhis2.cache.data.DataElementGroups;

@RestController
class DataElementGroupController {

  private final Dhis2ElementGroupCache groupCache;

  DataElementGroupController(Dhis2ElementGroupCache groupCache) {
    this.groupCache = groupCache;
  }

  @GetMapping("/dataElementGroups")
  DataElementGroups<DataElementGroup> all() {
    return new DataElementGroups<DataElementGroup>(groupCache.all());
  }

  @GetMapping("/dataElementGroups/{id}")
  DataElementGroup one(@PathVariable String id) {
    return groupCache
        .findById(id)
        .orElseThrow(() -> new DataElementGroupNotFoundException(id));
  }
  
}