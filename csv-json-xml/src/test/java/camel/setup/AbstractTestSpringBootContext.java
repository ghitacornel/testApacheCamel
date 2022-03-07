package camel.setup;


import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest
@EnableAutoConfiguration
public abstract class AbstractTestSpringBootContext {
}
