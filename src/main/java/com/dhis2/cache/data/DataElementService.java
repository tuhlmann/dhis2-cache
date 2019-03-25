package com.dhis2.cache.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dhis2.cache.json.DataElementGroups;
import com.dhis2.cache.json.DataElements;

@Component
public class DataElementService {
  
  DataElementService() {
  }
  
  public List<DataElementGroup> genDataElementGroups(DataElementGroups dataElementGroups) {
    List<DataElementGroup> groups = new ArrayList<DataElementGroup>();
    
    // Create entity objects for groups and elements
    for (com.dhis2.cache.json.DataElementGroup jsonGrp : dataElementGroups.getDataElementGroups()) {
      DataElementGroup g = new DataElementGroup();
      g.setGroupId(jsonGrp.getId());
      g.setDisplayName(jsonGrp.getDisplayName());
      g.setMembers(jsonGrp.getDataElements().stream().map(el -> el.getId()).collect(Collectors.toList()));
      groups.add(g);
    }
    
    return groups;
        
  }

  public List<DataElement> genDataElements(DataElements dataElements) {
    List<DataElement> elements = new ArrayList<DataElement>();
    
    for (com.dhis2.cache.json.DataElement jsonEl : dataElements.getDataElements()) {
      DataElement el = new DataElement();
      el.setElementId(jsonEl.getId());
      el.setDisplayName(jsonEl.getDisplayName());
      el.setGroups(jsonEl.getDataElementGroups().stream().map(g -> g.getId()).collect(Collectors.toList()));
      elements.add(el);
    }
    
    return elements;
    
  }
  
}
