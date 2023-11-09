package camel;

import camel.route.SplitRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class SplitRouteTest extends CamelTestSupport {

    @Override
    public RoutesBuilder createRouteBuilder() {
        return new SplitRoute();
    }

    @Test
    public void testRoute() throws Exception {

        // advice the start route using the inlined AdviceWith lambda style route builder
        // which has extended capabilities than the regular route builder
        // mock all endpoints
        AdviceWith.adviceWith(context, "simple-split", AdviceWithRouteBuilder::mockEndpoints);

        getMockEndpoint("mock:direct:start").expectedBodiesReceived("1,2,3");
        getMockEndpoint("mock:direct:end").expectedBodiesReceived("1", "2", "3");

        template.sendBody("direct:start", "1,2,3");

        MockEndpoint.assertIsSatisfied(context);
    }
}
