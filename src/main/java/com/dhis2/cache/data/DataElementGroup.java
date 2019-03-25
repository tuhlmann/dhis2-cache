package com.dhis2.cache.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
@JacksonXmlRootElement(localName = "DataElementGroup")
public class DataElementGroup {

  @JsonProperty("id")
  private String groupId;
  
  @JsonProperty("name")
  @JacksonXmlProperty(localName = "name")  
  private String displayName;
  
  @JacksonXmlProperty(localName = "member")
  @JacksonXmlElementWrapper(localName = "members")
  private List<String> members;

}