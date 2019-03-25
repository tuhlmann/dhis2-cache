package com.dhis2.cache.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class DataElement {

  @JsonProperty("id")
  private String elementId;
  
  @JsonProperty("name")
  @JacksonXmlProperty(localName = "name")
  private String displayName;

  @JacksonXmlProperty(localName = "group")
  @JacksonXmlElementWrapper(localName = "groups")
  private List<String> groups;
  
}
