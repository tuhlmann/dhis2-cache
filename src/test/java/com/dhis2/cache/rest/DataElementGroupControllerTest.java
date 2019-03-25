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

import com.dhis2.cache.DataElementGroupRepository;
import com.dhis2.cache.data.DataElementGroup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles("test")
public class DataElementGroupControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DataElementGroupRepository mockRepository;

  private final static String GROUP_ID = "myGroupId1";
  private final static String DISPLAY_NAME = "A sample group";
  
  @Before
  public void init() {
    DataElementGroup group1 = new DataElementGroup();
    group1.setDisplayName(DISPLAY_NAME);
    group1.setGroupId(GROUP_ID);
    group1.setMembers(Arrays.asList("element1", "element2", "element3"));
    when(mockRepository.findByGroupId(GROUP_ID)).thenReturn(Optional.of(group1));

    DataElementGroup group2 = new DataElementGroup();
    group2.setDisplayName("A second group");
    group2.setGroupId("myGroupId2");
    group2.setMembers(Arrays.asList("elementA", "elementB", "elementC"));
    when(mockRepository.findAll()).thenReturn(Arrays.asList(group1, group2));
  }

  // Expect a username=user and password=password user to be able to access the endpoint
  @WithMockUser
  @Test
  public void list_json_login_ok() throws Exception {

    mockMvc
      .perform(get(String.format("/dataElementGroups", GROUP_ID)))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andDo(document("list-dataElementGroups"));
  }

  @WithMockUser
  @Test
  public void list_xml_login_ok() throws Exception {
    
    mockMvc
    .perform(get(String.format("/dataElementGroups.xml", GROUP_ID)))
    .andDo(print())
    .andExpect(content().contentType("application/xml;charset=UTF-8"))
    .andExpect(status().isOk())
    .andExpect(xpath("DataElementGroup").nodeCount(2))
    .andDo(document("list-dataElementGroups"));
  }
  
  @WithMockUser
  @Test
  public void find_json_login_ok() throws Exception {
    
    mockMvc
    .perform(get(String.format("/dataElementGroups/%s", GROUP_ID)))
    .andDo(print())
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id", is(GROUP_ID)))
    .andExpect(jsonPath("$.displayName", is(DISPLAY_NAME)))
    .andExpect(jsonPath("$.members", hasSize(3)))
    .andExpect(jsonPath("$.members", contains("element1", "element2", "element3")))
    .andDo(document("find-dataElementGroup"));
  }
  
  @WithMockUser
  @Test
  public void find_json2_login_ok() throws Exception {
    
    mockMvc
    .perform(get(String.format("/dataElementGroups/%s", GROUP_ID))
    .header("Accept", "*/*"))
    .andDo(print())
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id", is(GROUP_ID)))
    .andExpect(jsonPath("$.displayName", is(DISPLAY_NAME)))
    .andExpect(jsonPath("$.members", hasSize(3)))
    .andExpect(jsonPath("$.members", contains("element1", "element2", "element3")))
    .andDo(document("find-dataElementGroup"));
  }
  
  @WithMockUser
  @Test
  public void find_json3_login_ok() throws Exception {
    
    mockMvc
    .perform(get(String.format("/dataElementGroups/%s", GROUP_ID))
    .header("Accept", "application/json"))
    .andDo(print())
    .andExpect(status().isOk())
    .andExpect(jsonPath("$.id", is(GROUP_ID)))
    .andExpect(jsonPath("$.displayName", is(DISPLAY_NAME)))
    .andExpect(jsonPath("$.members", hasSize(3)))
    .andExpect(jsonPath("$.members", contains("element1", "element2", "element3")))
    .andDo(document("find-dataElementGroup"));
  }  

  @WithMockUser
  @Test
  public void find_xml_login_ok() throws Exception {
    
    mockMvc
    .perform(get(String.format("/dataElementGroups/%s", GROUP_ID))
    .header("Accept", "application/xml"))
    .andDo(print())
    .andExpect(content().contentType("application/xml;charset=UTF-8"))
    .andExpect(status().isOk())
    .andExpect(xpath("DataElementGroup/id").string(is(GROUP_ID)))
    .andExpect(xpath("DataElementGroup/displayName").string(is(DISPLAY_NAME)))
    .andExpect(xpath("DataElementGroup/members/members").nodeCount(3))
    .andExpect(xpath("DataElementGroup/members/members[1]").string(is("element1")))
    .andExpect(xpath("DataElementGroup/members/members[2]").string(is("element2")))
    .andExpect(xpath("DataElementGroup/members/members[3]").string(is("element3")))
    .andDo(document("find-dataElementGroup"));
  }
  
  @WithMockUser
  @Test
  public void find_xml2_login_ok() throws Exception {
    
    mockMvc
    .perform(get(String.format("/dataElementGroups/%s.xml", GROUP_ID)))
    .andDo(print())
    .andExpect(content().contentType("application/xml;charset=UTF-8"))
    .andExpect(status().isOk())
    .andExpect(xpath("DataElementGroup/id").string(is(GROUP_ID)))
    .andExpect(xpath("DataElementGroup/displayName").string(is(DISPLAY_NAME)))
    .andExpect(xpath("DataElementGroup/members/members").nodeCount(3))
    .andExpect(xpath("DataElementGroup/members/members[1]").string(is("element1")))
    .andExpect(xpath("DataElementGroup/members/members[2]").string(is("element2")))
    .andExpect(xpath("DataElementGroup/members/members[3]").string(is("element3")))
    .andDo(document("find-dataElementGroup"));
  }
  
  @Test
  public void find_item_nologin_401() throws Exception {
    mockMvc
      .perform(get(String.format("/dataElementGroups/%s", GROUP_ID)))
      .andDo(print())
      .andExpect(status().isUnauthorized());
  }

  @Test
  public void find_all_nologin_401() throws Exception {
    mockMvc
      .perform(get("/dataElementGroups"))
      .andDo(print())
      .andExpect(status().isUnauthorized());
  }
  
  @WithMockUser
  @Test
  public void find_item_login_404() throws Exception {
    mockMvc
      .perform(get("/dataElementGroups/xyz"))
      .andDo(print())
      .andExpect(status().isNotFound());
  }
  
}
