package camel;

import camel.model.CustomMessage;
import camel.repository.MessageDbRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RouteTest {

    // Spring will inject the random port assigned to the web server
    @LocalServerPort
    int webServerPort;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TestRestTemplate template;

    @Autowired
    MessageDbRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void testPostNoProcessor() throws Exception {
        CustomMessage request = new CustomMessage(1);
        ResponseEntity<String> response = template.postForEntity("http://localhost:" + webServerPort + "/xxx/jms", objectMapper.writeValueAsString(request), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNull();
    }


}
