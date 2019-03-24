package com.dhis2.metaCache.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dhis2.metaCache.DataElementGroupRepository;
import com.dhis2.metaCache.data.DataElementGroup;

@RestController
class DataElementGroupController {

  private final DataElementGroupRepository repository;

  DataElementGroupController(DataElementGroupRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/dataElementGroups")
  Iterable<DataElementGroup> all() {
    return repository.findAll();
  }

  @GetMapping("/dataElementGroups/{id}")
  DataElementGroup one(@PathVariable String id) {

    return repository.findByGroupId(id)
      .orElseThrow(() -> new DataElementGroupNotFoundException(id));
  }

}