package com.dhis2.cache.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dhis2.cache.DataElementRepository;
import com.dhis2.cache.data.DataElement;
import com.dhis2.cache.data.DataElements;

@RestController
class DataElementController {

  private final DataElementRepository repository;

  DataElementController(DataElementRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/dataElements")
  DataElements<DataElement> all() {
    DataElements<DataElement> elements = new DataElements<>();
    repository.findAll().iterator().forEachRemaining(elements::add);
    return elements;
  }

  @GetMapping("/dataElements/{id}")
  DataElement one(@PathVariable String id) {

    return repository.findByElementId(id)
      .orElseThrow(() -> new DataElementNotFoundException(id));
  }

}