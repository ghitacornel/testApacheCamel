package camel;

import camel.route.WiretapRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class WiretapRouteTest extends CamelTestSupport {

    @Override
    public RoutesBuilder createRouteBuilder() {
        return new WiretapRoute();
    }

    @Test
    public void testRoute() throws Exception {

        // advice the start route using the inlined AdviceWith lambda style route builder
        // which has extended capabilities than the regular route builder
        // mock all endpoints
        AdviceWith.adviceWith(context, "simple-wiretap", AdviceWithRouteBuilder::mockEndpoints);

        getMockEndpoint("mock:direct:start").expectedBodiesReceived("something");
        getMockEndpoint("mock:direct:end").expectedBodiesReceived("something");
        getMockEndpoint("mock:direct:check1").expectedBodiesReceived("something");

        template.sendBody("direct:start", "something");

        MockEndpoint.assertIsSatisfied(context);
    }
}
