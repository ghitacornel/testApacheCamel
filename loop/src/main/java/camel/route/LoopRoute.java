package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class LoopRoute extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:start1")
                .routeId("simple-loop")
                .log("initial message ${body}")
                .loop(3)
                .transform(body().append("B"))
                .log("repeated message ${body}")
                .end();

        from("direct:start2")
                .routeId("simple-loop-copy")
                .log("initial message ${body}")
                .loop(3)
                .copy()// repeat the initial message, do not reuse it
                .transform(body().append("B"))
                .log("repeated message ${body}")
                .end();
    }
}
