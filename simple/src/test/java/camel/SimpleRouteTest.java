package camel;

import camel.route.steps.FinalStep;
import camel.route.steps.InitialStep;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SimpleRouteTest {

    // can build producer from injected context also
//    @Autowired
//    CamelContext context;
//    ProducerTemplate template = context.createProducerTemplate();
    @Produce("direct:start")
    ProducerTemplate template;

    @Autowired
    InitialStep initialStep;

    @Autowired
    FinalStep finalStep;

    @Test
    public void testRoute() {

        // create a dummy producer and send a dummy starting message
        template.sendBody("direct:start", "This is just a dummy startup message. It will be ignored");

        Assertions.assertThat(initialStep.testDate).isNotNull();
        Assertions.assertThat(finalStep.testString).isEqualTo((initialStep.testDate.getTime() + 222) + " 3");

    }
}
