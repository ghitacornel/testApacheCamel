package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class WiretapRoute extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:start")
                .routeId("simple-wiretap")
                .wireTap("direct:check1")
                .to("direct:end");

        from("direct:end").log("${body}").end();
        from("direct:check1").log("${body}").end();
    }
}
