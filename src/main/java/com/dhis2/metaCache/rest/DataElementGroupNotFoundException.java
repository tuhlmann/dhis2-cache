package com.dhis2.metaCache.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataElementGroupNotFoundException extends RuntimeException {

  public DataElementGroupNotFoundException(String id) {
    super("Could not find data element group with db id " + id);
  }
}