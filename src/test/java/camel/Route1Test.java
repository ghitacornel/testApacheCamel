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
import org.apache.camel.util.function.ThrowingConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockEndpoints
public class Route1Test {

    @Autowired
    private CamelContext context;

    @Produce("mock:file")
    protected ProducerTemplate start;

    @BeforeEach
    public void before() throws Exception {
        AdviceWith.adviceWith(context, "route1", a -> {
                    a.weaveAddLast().to("mock:stop");
                }
        );
    }

    @Test
    public void testRoute() throws Exception {

        start.sendBody("xxx");

        TimeUnit.SECONDS.sleep(2);
        Assertions.assertTrue(true);
    }
}
