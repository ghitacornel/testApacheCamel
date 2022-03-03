package camel;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.apache.camel.test.spring.junit5.MockEndpointsAndSkip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockEndpoints
public class Route1Test {

    @Autowired
    private CamelContext camelContext;

    @Produce("mock:file")
    protected ProducerTemplate start;

    @Test
    public void testRoute() throws Exception {

        start.sendBody("xxx");

        AdviceWith.adviceWith(camelContext, "route1", a -> a.mockEndpointsAndSkip("*"));

        TimeUnit.SECONDS.sleep(2);
        Assertions.assertTrue(true);
    }
}
