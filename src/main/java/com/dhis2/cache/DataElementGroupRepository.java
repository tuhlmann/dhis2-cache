package com.dhis2.cache;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dhis2.cache.data.DataElementGroup;

public interface DataElementGroupRepository extends CrudRepository<DataElementGroup, Long> {
  Optional<DataElementGroup> findByGroupId(String groupId);
}