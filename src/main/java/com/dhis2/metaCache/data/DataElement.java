package com.dhis2.metaCache.data;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
  private String displayName;

  @ElementCollection
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
