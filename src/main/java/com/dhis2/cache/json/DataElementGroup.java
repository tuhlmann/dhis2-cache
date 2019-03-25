package com.dhis2.cache.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class DataElementGroup {

  private String id;
  private String displayName;
  
  private List<WrappedElementId> dataElements;
  
}