package camel;

import camel.route.SimpleRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class SimpleRouteTest extends CamelTestSupport {

    @Override
    public RoutesBuilder createRouteBuilder() {
        return new SimpleRoute();
    }

    @Test
    public void testRoute() throws Exception {

        // advice the start route using the inlined AdviceWith lambda style route builder
        // which has extended capabilities than the regular route builder
        // mock all endpoints
        AdviceWith.adviceWith(context, "simple-route", adviceWithRouteBuilder -> {
            adviceWithRouteBuilder.weaveAddLast().to("mock:result").end();
            adviceWithRouteBuilder.mockEndpoints();
        });

        getMockEndpoint("mock:direct:start").expectedBodiesReceived("Hello");
        getMockEndpoint("mock:result").expectedBodiesReceived("Hello_AddedValueBySimpleProcessor_AddedValueByLambdaProcessor");

        String input = "Hello";
        template.sendBody("direct:start", input);

        MockEndpoint.assertIsSatisfied(context);
    }
}
