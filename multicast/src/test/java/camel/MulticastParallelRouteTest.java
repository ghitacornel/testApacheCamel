package camel;

import camel.route.MulticastParallelRoute;
import lombok.SneakyThrows;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class MulticastParallelRouteTest extends CamelTestSupport {

    @Override
    public RoutesBuilder createRouteBuilder() {
        return new MulticastParallelRoute();
    }

    @SneakyThrows
    @Test
    public void testRoute() {

        // advice the start route using the inlined AdviceWith lambda style route builder
        // which has extended capabilities than the regular route builder
        // mock all endpoints
        AdviceWith.adviceWith(context, "simple-multicast-parallel-route", adviceWithRouteBuilder -> {
            adviceWithRouteBuilder.weaveAddLast().to("mock:result").end();
            adviceWithRouteBuilder.mockEndpoints();
        });

        Object input = new Object();

        getMockEndpoint("mock:direct:start").expectedBodiesReceived(input);
        getMockEndpoint("mock:direct:parallelFlow1").expectedBodiesReceived(input);
        getMockEndpoint("mock:direct:parallelFlow2").expectedBodiesReceived(input);
        getMockEndpoint("mock:direct:parallelFlow3").expectedBodiesReceived(input);

        template.sendBody("direct:start", input);

        MockEndpoint.assertIsSatisfied(context);
    }
}
