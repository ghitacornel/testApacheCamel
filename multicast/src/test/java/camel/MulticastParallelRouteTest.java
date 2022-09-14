package camel;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MulticastParallelRouteTest {

    @Produce("direct:start")
    ProducerTemplate template;

    @Test
    public void testRoute() {

        // create a dummy producer and send a dummy starting message
        template.sendBody("direct:start", "This is just a dummy startup message. It will be ignored");

        // check the logs and verify the parallel processing

    }
}
