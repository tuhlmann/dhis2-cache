package com.dhis2.cache;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dhis2.cache.data.DataElement;

public interface DataElementRepository extends CrudRepository<DataElement, Long> {
  Optional<DataElement> findByElementId(String elementId);
}