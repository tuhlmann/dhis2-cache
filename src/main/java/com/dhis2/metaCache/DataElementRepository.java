package com.dhis2.metaCache;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dhis2.metaCache.data.DataElement;

public interface DataElementRepository extends CrudRepository<DataElement, Long> {
  Optional<DataElement> findByElementId(String elementId);
}