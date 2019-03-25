package com.dhis2.cache.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class DataElementGroups {
  
  private List<DataElementGroup> dataElementGroups;

  public Map<String, DataElementGroup> getAsMap() {
    if (dataElementGroups != null) {
      Map<String, DataElementGroup> map =  
        dataElementGroups.stream().collect(Collectors.toMap(DataElementGroup::getId, g -> g));
      return map;
    } else {
      return new HashMap<>();
    }
  }
}