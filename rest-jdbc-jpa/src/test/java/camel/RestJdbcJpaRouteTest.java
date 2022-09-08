package camel;

import camel.route.model.PersonRequest;
import camel.route.repository.PersonRepository;
import camel.route.repository.entity.Person;
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
public class RestJdbcJpaRouteTest {

    // Spring will inject the random port assigned to the web server
    @LocalServerPort
    int webServerPort;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TestRestTemplate template;

    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        personRepository.deleteAll();
        personRepository.save(Person.builder().id(1).name("first").age(111).build());
    }

    @Test
    public void testGet() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:" + webServerPort + "/camel/api/1", String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":1,\"name\":\"first\",\"age\":111}");
    }

    @Test
    public void testPostNoId() throws Exception {
        PersonRequest personRequest = new PersonRequest("none", 2, "ion", 11);
        ResponseEntity<String> response = template.postForEntity("http://localhost:" + webServerPort + "/camel/api", objectMapper.writeValueAsString(personRequest), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":null,\"name\":\"ion\",\"age\":11}");
    }

    @Test
    public void testPostJPAId() throws Exception {
        PersonRequest personRequest = new PersonRequest("jpa", 3, "gheorge", 12);
        ResponseEntity<String> response = template.postForEntity("http://localhost:" + webServerPort + "/camel/api", objectMapper.writeValueAsString(personRequest), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":3,\"name\":\"gheorge\",\"age\":12}");
    }

    @Test
    public void testPostJDBCId() throws Exception {
        PersonRequest personRequest = new PersonRequest("jdbc", 4, "vasile", 13);
        ResponseEntity<String> response = template.postForEntity("http://localhost:" + webServerPort + "/camel/api", objectMapper.writeValueAsString(personRequest), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("{\"id\":4,\"name\":\"vasile\",\"age\":13}");
    }

}
