package com.dhis2.cache.data;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "DataElementGroups")
public class DataElementGroups<T> extends ArrayList<T> {
  
  public DataElementGroups(Collection<T> col) {
    super(col);
  }
  
}
