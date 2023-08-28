package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class MulticastParallelRoute extends RouteBuilder {


    @Override
    public void configure() {
        from("direct:start")
                .routeId("simple-multicast-parallel-route")
                .log("initial body : ${body}")
                .multicast()
                .parallelProcessing()
                .to("direct:parallelFlow1")
                .to("direct:parallelFlow2")
                .to("direct:parallelFlow3")
                .end();

        from("direct:parallelFlow1")
                .process(exchange -> {
                    exchange.getMessage().setBody(exchange.getMessage().getBody() + " " + Thread.currentThread() + " parallelFlow1");
                })
                .log("after parallel flow 1 : ${body}")
                .end();

        from("direct:parallelFlow2")
                .process(exchange -> {
                    exchange.getMessage().setBody(exchange.getMessage().getBody() + " " + Thread.currentThread() + " parallelFlow2");
                })
                .log("after parallel flow 2 : ${body}")
                .end();

        from("direct:parallelFlow3")
                .process(exchange -> {
                    exchange.getMessage().setBody(exchange.getMessage().getBody() + " " + Thread.currentThread() + " parallelFlow3");
                })
                .log("after parallel flow 3 : ${body}")
                .end();

    }
}

