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
                .log("before lambda processor body = " + body().toString())
                .process(exchange ->
                        exchange.getMessage().setBody(exchange.getMessage().getBody(String.class) + "_AddedValueByLambdaProcessor")
                )
                .log("after lambda processor body = " + body().toString())
                .log("end of the route")
                .end()
        ;
    }
}

