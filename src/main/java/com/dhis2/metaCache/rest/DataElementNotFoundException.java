package com.dhis2.metaCache.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataElementNotFoundException extends RuntimeException {

  public DataElementNotFoundException(String id) {
    super("Could not find data element with db id " + id);
  }
}