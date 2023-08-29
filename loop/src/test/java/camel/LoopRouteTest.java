package camel;

import camel.route.LoopRoute;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class LoopRouteTest extends CamelTestSupport {

    @Override
    public RoutesBuilder createRouteBuilder() {
        return new LoopRoute();
    }

    @Test
    public void testRouteLoop() throws Exception {

        // advice the start route using the inlined AdviceWith lambda style route builder
        // which has extended capabilities than the regular route builder
        // mock all endpoints
        AdviceWith.adviceWith(context, "simple-loop", adviceWithRouteBuilder -> {
            adviceWithRouteBuilder.weaveAddLast().to("mock:result").end();
            adviceWithRouteBuilder.mockEndpoints();
        });

        getMockEndpoint("mock:direct:start1").expectedBodiesReceived("one");
        getMockEndpoint("mock:result").expectedBodiesReceived("oneBBB");

        template.sendBody("direct:start1", "one");

        MockEndpoint.assertIsSatisfied(context);
    }

    @Test
    public void testRouteLoopCopy() throws Exception {

        // advice the start route using the inlined AdviceWith lambda style route builder
        // which has extended capabilities than the regular route builder
        // mock all endpoints
        AdviceWith.adviceWith(context, "simple-loop-copy", adviceWithRouteBuilder -> {
            adviceWithRouteBuilder.weaveAddLast().to("mock:result").end();
            adviceWithRouteBuilder.mockEndpoints();
        });

        getMockEndpoint("mock:direct:start2").expectedBodiesReceived("two");
        getMockEndpoint("mock:result").expectedBodiesReceived("twoB");

        template.sendBody("direct:start2", "two");

        MockEndpoint.assertIsSatisfied(context);
    }
}
