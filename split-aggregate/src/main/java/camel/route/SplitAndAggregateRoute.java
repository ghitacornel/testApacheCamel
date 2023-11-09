package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class SplitAndAggregateRoute extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:start")
                .routeId("simple-split-and-aggregate")
                .log("initial ${body}")
                .split(body().tokenize(","), (oldExchange, newExchange) -> {
                    if (oldExchange == null) {
                        return newExchange;
                    }
                    newExchange.getMessage().setBody(oldExchange.getMessage().getBody(String.class) + ";" + newExchange.getMessage().getBody(String.class));
                    return newExchange;
                })
                .log("tokenized ${body}")
                .end()
                .to("direct:end")
                .end();

        from("direct:end")
                .log("combined ${body}")
                .end();

    }
}
