package com.dhis2.cache.data;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "DataElements")
public class DataElements<T> extends ArrayList<T> {
  
  public DataElements(Collection<T> col) {
    super(col);
  }

}
