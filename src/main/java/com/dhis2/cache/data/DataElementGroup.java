package com.dhis2.cache.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement(localName = "DataElementGroup")
public class DataElementGroup {

  private @Id @GeneratedValue Long id;

  private String groupId;
  
  @JsonProperty("name")
  @JacksonXmlProperty(localName = "name")  
  private String displayName;
  
  @ElementCollection
  @JacksonXmlProperty(localName = "member")
  @JacksonXmlElementWrapper(localName = "members")
  private List<String> members;

  @JsonProperty("id")
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }
  
  @JsonProperty("id")
  public String getGroupId() {
    return this.groupId;
  }
  
}