package camel;

import camel.route.ChoiceRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class ChoiceRouteTest extends CamelTestSupport {

    @Override
    public RoutesBuilder createRouteBuilder() {
        return new ChoiceRoute();
    }

    @Test
    public void testRoute() throws Exception {

        // advice the start route using the inlined AdviceWith lambda style route builder
        // which has extended capabilities than the regular route builder
        // mock all endpoints
        AdviceWith.adviceWith(context, "simple-choice", AdviceWithRouteBuilder::mockEndpoints);

        getMockEndpoint("mock:direct:start").expectedBodiesReceived("one", "two", "three");
        getMockEndpoint("mock:direct:a").expectedBodiesReceived("one");
        getMockEndpoint("mock:direct:b").expectedBodiesReceived("two");
        getMockEndpoint("mock:direct:c").expectedBodiesReceived("three");

        template.sendBody("direct:start", "one");
        template.sendBody("direct:start", "two");
        template.sendBody("direct:start", "three");

        MockEndpoint.assertIsSatisfied(context);
    }
}
