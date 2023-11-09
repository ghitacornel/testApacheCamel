package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class SplitRoute extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:start")
                .routeId("simple-split")
                .log("initial ${body}")
                .split(body().tokenize(","))
                .log("tokenized ${body}")
                .to("direct:end")
                .end();

        from("direct:end")
                .log("${body}")
                .end();

    }
}
