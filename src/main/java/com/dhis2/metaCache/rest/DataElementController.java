package com.dhis2.metaCache.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dhis2.metaCache.DataElementGroupRepository;
import com.dhis2.metaCache.DataElementRepository;
import com.dhis2.metaCache.data.DataElement;
import com.dhis2.metaCache.data.DataElementGroup;

@RestController
class DataElementController {

  private final DataElementRepository repository;

  DataElementController(DataElementRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/dataElements")
  Iterable<DataElement> all() {
    return repository.findAll();
  }

  @GetMapping("/dataElements/{id}")
  DataElement one(@PathVariable String id) {

    return repository.findByElementId(id)
      .orElseThrow(() -> new DataElementNotFoundException(id));
  }

}