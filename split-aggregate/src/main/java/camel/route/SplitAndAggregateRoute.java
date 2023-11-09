package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class SplitAndAggregateRoute extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:start")
                .routeId("simple-split-and-aggregate")
                .log("initial ${body}")
                .split(body().tokenize(","))
                .log("tokenized ${body}")
                .end()
                .to("direct:end")
                .end();

        from("direct:end")
                .log("combined ${body}")
                .end();

    }
}
