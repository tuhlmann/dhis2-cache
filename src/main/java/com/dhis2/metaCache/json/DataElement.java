package com.dhis2.metaCache.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class DataElement {

  private String id;
  private String displayName;

  private List<WrappedElementId> dataElementGroups;
    
}
