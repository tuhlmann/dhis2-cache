package com.dhis2.cache;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import wiremock.org.custommonkey.xmlunit.XMLAssert;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
@ActiveProfiles("test")
// @Slf4j
public class Dhis2IntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;
  
  private HttpHeaders headers = new HttpHeaders();
  
  private String baseUrl() {
    return "http://localhost:" + port;
  }
  
  private final static String TEST_USER = "user";
  private final static String TEST_PWD = "password";
      
  // TODO: Make the mock port dynamic and inject it into test props 
  @ClassRule 
  public static WireMockRule wireMockRule = new WireMockRule( wireMockConfig().port(8090).httpsPort(8443).notifier(new ConsoleNotifier(true)));

  @BeforeClass
  public static void init() {
      wireMockRule.stubFor(get(urlPathMatching("/api/29/dataElementGroups.json.*"))
        .withBasicAuth("wiremock", "wiremock")
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .withBodyFile("sourceDataElementGroups.json")));    

      wireMockRule.stubFor(get(urlPathMatching("/api/29/dataElements.json.*"))
        .withBasicAuth("wiremock", "wiremock")
        .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .withBodyFile("sourceDataElements.json")));    
  }
  
  private String fileContents(String path) {
    try {
      return new String(Files.readAllBytes(new ClassPathResource(path).getFile().toPath()));
    } catch (IOException e) {
      throw new IllegalStateException("Could not read file " + path, e);
    }
  }
  
  @Test
  public void accessingTheApiShouldResultIn404() throws Exception {
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = restTemplate.exchange(
        String.format("%s/dataElementGroups", baseUrl()),
        HttpMethod.GET, entity, String.class);

    assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    
    assertThat(response.getBody())
      .contains("\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Unauthorized\",\"path\":\"/dataElementGroups\"");
  }  
  
  @Test
  public void getGroupsJsonShouldReturnAList() throws Exception {
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = restTemplate.withBasicAuth(TEST_USER, TEST_PWD).exchange(
        String.format("%s/dataElementGroups", baseUrl()),
        HttpMethod.GET, entity, String.class);

    JSONAssert.assertEquals(fileContents("__files/cachedDataElementGroups.json"), response.getBody(), true);
  }  
  
  @Test
  public void getGroupsXmlShouldReturnAList() throws Exception {
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);
    
    ResponseEntity<String> response = restTemplate.withBasicAuth(TEST_USER, TEST_PWD).exchange(
        String.format("%s/dataElementGroups.xml", baseUrl()),
        HttpMethod.GET, entity, String.class);
    
    XMLAssert.assertXMLEqual(fileContents("__files/cachedDataElementGroups.xml"), response.getBody());
  }  
  
  @Test
  public void getOneGroupJsonShouldReturnAnObject() throws Exception {
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = restTemplate.withBasicAuth(TEST_USER, TEST_PWD).exchange(
        String.format("%s/dataElementGroups/oDkJh5Ddh7d", baseUrl()),
        HttpMethod.GET, entity, String.class);

    JSONAssert.assertEquals(fileContents("__files/cachedDataElementGroup_1.json"), response.getBody(), true);
  }  
    
  @Test
  public void getElementsJsonShouldReturnAList() throws Exception {
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = restTemplate.withBasicAuth(TEST_USER, TEST_PWD).exchange(
        String.format("%s/dataElements", baseUrl()),
        HttpMethod.GET, entity, String.class);

    JSONAssert.assertEquals(fileContents("__files/cachedDataElements.json"), response.getBody(), true);
  }  
  
  @Test
  public void getElementsXmlShouldReturnAList() throws Exception {
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);
    
    ResponseEntity<String> response = restTemplate.withBasicAuth(TEST_USER, TEST_PWD).exchange(
        String.format("%s/dataElements.xml", baseUrl()),
        HttpMethod.GET, entity, String.class);
    
    XMLAssert.assertXMLEqual(fileContents("__files/cachedDataElements.xml"), response.getBody());
  }  
  
  @Test
  public void getOneElementJsonShouldReturnAnObject() throws Exception {
    HttpEntity<String> entity = new HttpEntity<String>(null, headers);

    ResponseEntity<String> response = restTemplate.withBasicAuth(TEST_USER, TEST_PWD).exchange(
        String.format("%s/dataElements/FTRrcoaog83", baseUrl()),
        HttpMethod.GET, entity, String.class);

    JSONAssert.assertEquals(fileContents("__files/cachedDataElement_1.json"), response.getBody(), true);
  }    
    
}
