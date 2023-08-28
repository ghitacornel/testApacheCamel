package camel;

import camel.route.FilterRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class FilterRouteTest extends CamelTestSupport {

    @Override
    public RoutesBuilder createRouteBuilder() {
        return new FilterRoute();
    }

    @Test
    public void testRoute() throws Exception {

        // advice the start route using the inlined AdviceWith lambda style route builder
        // which has extended capabilities than the regular route builder
        // mock all endpoints
        AdviceWith.adviceWith(context, "simple-filter", AdviceWithRouteBuilder::mockEndpoints);

        getMockEndpoint("mock:direct:start").expectedBodiesReceived("dumpItOne", "dumpItTwo", "KeepIt", "dumpItThree");
        getMockEndpoint("mock:direct:end").expectedBodiesReceived("KeepIt");

        template.sendBody("direct:start", "dumpItOne");
        template.sendBody("direct:start", "dumpItTwo");
        template.sendBody("direct:start", "KeepIt");
        template.sendBody("direct:start", "dumpItThree");

        MockEndpoint.assertIsSatisfied(context);
    }
}
