package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class SplitRoute extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:start")
                .routeId("simple-split")
                .log("${body}")
        ;

    }
}
