package com.dhis2.cache.data;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class DataElement {

  private @Id @GeneratedValue Long id;

  private String elementId;
  
  @JsonProperty("name")
  @JacksonXmlProperty(localName = "name")
  private String displayName;

  @ElementCollection
  @JacksonXmlProperty(localName = "group")
  @JacksonXmlElementWrapper(localName = "groups")
  private List<String> groups;
  
  @JsonProperty("id")
  public void setElementId(String elementId) {
    this.elementId = elementId;
  }
  
  @JsonProperty("id")
  public String getElementId() {
    return this.elementId;
  }
    
}
