package camel.route;

import camel.route.steps.SimpleProcessor;
import org.apache.camel.builder.RouteBuilder;

public class SimpleRoute extends RouteBuilder {

    private final SimpleProcessor simpleProcessor = new SimpleProcessor();

    @Override
    public void configure() {
        from("direct:start")
                .routeId("simple-route")
                .log("start of the route")
                .log("before simpleProcessor body = " + body().toString())
                .bean(simpleProcessor)
                .log("after simpleProcessor body = " + body().toString())
                .log("end of the route")
                .end()
        ;
    }
}

