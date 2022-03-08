package camel;

import camel.route.steps.FinalStep;
import camel.route.steps.InitialStep;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteTest {

    @Autowired
    CamelContext context;

    @Autowired
    InitialStep initialStep;

    @Autowired
    FinalStep finalStep;

    @Test
    public void testRoute() {

        // create a dummy producer and send a dummy starting message
        ProducerTemplate template = context.createProducerTemplate();
        template.sendBody("direct:start", "This is just a dummy startup message. It will be ignored");

        Assertions.assertThat(initialStep.testDate).isNotNull();
        Assertions.assertThat(finalStep.testString).isEqualTo((initialStep.testDate.getTime() + 222) + " 3");

    }
}
