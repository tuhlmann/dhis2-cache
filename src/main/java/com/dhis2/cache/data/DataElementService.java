package com.dhis2.cache.data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.dhis2.cache.json.DataElementGroups;
import com.dhis2.cache.json.DataElements;

@Component
public class DataElementService {
  
  DataElementService() {
  }
  
  public Map<String, DataElementGroup> genDataElementGroups(DataElementGroups dataElementGroups) {
    Map<String, DataElementGroup> groupMap = new HashMap<>();
    
    for (com.dhis2.cache.json.DataElementGroup jsonGrp : dataElementGroups.getDataElementGroups()) {
      DataElementGroup g = new DataElementGroup();
      g.setGroupId(jsonGrp.getId());
      g.setDisplayName(jsonGrp.getDisplayName());
      g.setMembers(jsonGrp.getDataElements().stream().map(el -> el.getId()).collect(Collectors.toList()));
      groupMap.put(jsonGrp.getId(), g);
    }
    
    return groupMap;
        
  }

  public Map<String, DataElement> genDataElements(DataElements dataElements) {
    Map<String, DataElement> elementMap = new HashMap<>();
    
    for (com.dhis2.cache.json.DataElement jsonEl : dataElements.getDataElements()) {
      DataElement el = new DataElement();
      el.setElementId(jsonEl.getId());
      el.setDisplayName(jsonEl.getDisplayName());
      el.setGroups(jsonEl.getDataElementGroups().stream().map(g -> g.getId()).collect(Collectors.toList()));
      elementMap.put(el.getElementId(), el);
    }
    
    return elementMap;
    
  }
  
}
