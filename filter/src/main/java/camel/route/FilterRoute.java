package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class FilterRoute extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:start")
                .routeId("simple-filter")
                .filter(exchange -> !exchange.getMessage().getBody(String.class).startsWith("dumpIt"))
                .to("direct:end");

        from("direct:end").log("kept message : ${body}").end();
    }
}
