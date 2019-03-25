package com.dhis2.cache.rest;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dhis2.cache.DataElementRepository;
import com.dhis2.cache.data.DataElement;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
public class DataElementControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DataElementRepository mockRepository;
  
  private final static String ELEMENT_ID = "myElementId1";
  private final static String DISPLAY_NAME = "A sample element";

  @Before
  public void init() {
    DataElement element1 = new DataElement();
    element1.setDisplayName(DISPLAY_NAME);
    element1.setElementId(ELEMENT_ID);
    element1.setGroups(Arrays.asList("group1", "group2", "group3"));
    when(mockRepository.findByElementId(ELEMENT_ID)).thenReturn(Optional.of(element1));
    
    DataElement element2 = new DataElement();
    element2.setDisplayName("A second element");
    element2.setElementId("myElementId2");
    element2.setGroups(Arrays.asList("groupA", "groupB", "groupC"));
    when(mockRepository.findAll()).thenReturn(Arrays.asList(element1, element2));
    
  }

  // Expect a username=user and password=password user to be able to access the endpoint
  @WithMockUser
  @Test
  public void list_json_login_ok() throws Exception {

    mockMvc
      .perform(get(String.format("/dataElements", ELEMENT_ID)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andDo(document("list-dataElements"));
  }
  
  @WithMockUser
  @Test
  public void find_json_login_ok() throws Exception {

    mockMvc
      .perform(get(String.format("/dataElements/%s", ELEMENT_ID)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(ELEMENT_ID)))
      .andExpect(jsonPath("$.displayName", is(DISPLAY_NAME)))
      .andExpect(jsonPath("$.groups", hasSize(3)))
      .andExpect(jsonPath("$.groups", contains("group1", "group2", "group3")))
      .andDo(document("find-dataElement"));
  }

  @WithMockUser
  @Test
  public void find_json2_login_ok() throws Exception {
    
    mockMvc
    .perform(get(String.format("/dataElements/%s", ELEMENT_ID))
    .header("Accept", "*/*"))
    .andDo(print())
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id", is(ELEMENT_ID)))
    .andExpect(jsonPath("$.displayName", is(DISPLAY_NAME)))
    .andExpect(jsonPath("$.groups", hasSize(3)))
    .andExpect(jsonPath("$.groups", contains("group1", "group2", "group3")))
    .andDo(document("find-dataElement"));
  }
  
  @WithMockUser
  @Test
  public void find_json3_login_ok() throws Exception {
    
    mockMvc
    .perform(get(String.format("/dataElements/%s", ELEMENT_ID))
    .header("Accept", "application/json"))
    .andDo(print())
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id", is(ELEMENT_ID)))
    .andExpect(jsonPath("$.displayName", is(DISPLAY_NAME)))
    .andExpect(jsonPath("$.groups", hasSize(3)))
    .andExpect(jsonPath("$.groups", contains("group1", "group2", "group3")))
    .andDo(document("find-dataElement"));
  }  

  @WithMockUser
  @Test
  public void find_xml_login_ok() throws Exception {
    
    mockMvc
    .perform(get(String.format("/dataElements/%s", ELEMENT_ID))
    .header("Accept", "application/xml"))
    .andDo(print())
    .andExpect(content().contentType("application/xml;charset=UTF-8"))
    .andExpect(status().isOk())
    .andExpect(xpath("DataElement/id").string(is(ELEMENT_ID)))
    .andExpect(xpath("DataElement/displayName").string(is(DISPLAY_NAME)))
    .andExpect(xpath("DataElement/groups/groups").nodeCount(3))
    .andExpect(xpath("DataElement/groups/groups[1]").string(is("group1")))
    .andExpect(xpath("DataElement/groups/groups[2]").string(is("group2")))
    .andExpect(xpath("DataElement/groups/groups[3]").string(is("group3")))
    .andDo(document("find-dataElement"));
  }
  
  @Test
  public void find_item_nologin_401() throws Exception {
    mockMvc
      .perform(get(String.format("/dataElements/%s", ELEMENT_ID)))
      .andDo(print())
      .andExpect(status().isUnauthorized());
  }

  @Test
  public void find_all_nologin_401() throws Exception {
    mockMvc
      .perform(get("/dataElements"))
      .andDo(print())
      .andExpect(status().isUnauthorized());
  }
  
  @WithMockUser
  @Test
  public void find_item_login_404() throws Exception {
    mockMvc
      .perform(get("/dataElements/xyz"))
      .andDo(print())
      .andExpect(status().isNotFound());
  }
  
}
