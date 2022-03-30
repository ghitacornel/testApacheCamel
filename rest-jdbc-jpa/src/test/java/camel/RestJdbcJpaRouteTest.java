package camel;

import camel.route.model.InputModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.CamelContext;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RestJdbcJpaRouteTest {

    @Autowired
    CamelContext context;

    // Spring will inject the random port assigned to the web server
    @LocalServerPort
    int webServerPort;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    TestRestTemplate template;

    @Test
    public void testGet() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + webServerPort + "/camel/api", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("simple response");
    }

    @Test
    public void testPostNoId() throws Exception {
        InputModel inputModel = new InputModel("aaa");
        ResponseEntity<String> response = template.postForEntity("http://localhost:" + webServerPort + "/camel/api", objectMapper.writeValueAsString(inputModel), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":\"no_id\",\"name\":\"aaa\"}");
    }

    @Test
    public void testPostJPAId() throws Exception {
        InputModel inputModel = new InputModel("jpa_aaa");
        ResponseEntity<String> response = template.postForEntity("http://localhost:" + webServerPort + "/camel/api", objectMapper.writeValueAsString(inputModel), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":\"jpa_id\",\"name\":\"jpa_aaa\"}");
    }

    @Test
    public void testPostJDBCId() throws Exception {
        InputModel inputModel = new InputModel("jdbc_aaa");
        ResponseEntity<String> response = template.postForEntity("http://localhost:" + webServerPort + "/camel/api", objectMapper.writeValueAsString(inputModel), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":\"jdbc_id\",\"name\":\"jdbc_aaa\"}");
    }

}
