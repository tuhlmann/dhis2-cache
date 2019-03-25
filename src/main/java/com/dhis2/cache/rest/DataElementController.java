package com.dhis2.cache.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dhis2.cache.Dhis2ElementCache;
import com.dhis2.cache.data.DataElement;
import com.dhis2.cache.data.DataElements;

@RestController
class DataElementController {

  private final Dhis2ElementCache elementCache;

  DataElementController(Dhis2ElementCache elementCache) {
    this.elementCache = elementCache;
  }

  @GetMapping("/dataElements")
  DataElements<DataElement> all() {
    return new DataElements<DataElement>(elementCache.all());
  }

  @GetMapping("/dataElements/{id}")
  DataElement one(@PathVariable String id) {
    return elementCache
        .findById(id)
        .orElseThrow(() -> new DataElementNotFoundException(id));
  }
  

}