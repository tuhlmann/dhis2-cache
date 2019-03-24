package com.dhis2.metaCache;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dhis2.metaCache.data.DataElementGroup;

public interface DataElementGroupRepository extends CrudRepository<DataElementGroup, Long> {
  Optional<DataElementGroup> findByGroupId(String groupId);
}