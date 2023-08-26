package camel.route;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;
import java.util.List;

public class MulticastParallelRoute extends RouteBuilder {

    private final Snapshot snapshot = new Snapshot();

    @Override
    public void configure() {
        from("direct:start")
                .routeId("simple-multicast-parallel-route")
                .log("start of the route")
                .bean((Processor) exchange -> {
                    List<String> logs = new ArrayList<>();
                    logs.add("initial step");
                    exchange.getMessage().setBody(logs);
                })
                .log("after initial step : ${body}")
                .multicast()
                .parallelProcessing()
                .to("direct:parallelFlow1")
                .to("direct:parallelFlow2")
                .to("direct:parallelFlow3")
                .end();

        from("direct:parallelFlow1")
                .process(exchange -> {
                    List<String> logs = new ArrayList<>();
                    logs.add("parallel step 1 executed by " + Thread.currentThread());
                    snapshot.getTrace().put("parallel step 1", Thread.currentThread());
                    exchange.getMessage().setBody(logs);
                })
                .log("after parallel flow 1 : ${body}")
                .end();

        from("direct:parallelFlow2")
                .process(exchange -> {
                    List<String> logs = new ArrayList<>();
                    logs.add("parallel step 2 executed by " + Thread.currentThread());
                    snapshot.getTrace().put("parallel step 2", Thread.currentThread());
                    exchange.getMessage().setBody(logs);
                })
                .log("after parallel flow 2 : ${body}")
                .end();

        from("direct:parallelFlow3")
                .process(exchange -> {
                    List<String> logs = new ArrayList<>();
                    logs.add("parallel step 3 executed by " + Thread.currentThread());
                    snapshot.getTrace().put("parallel step 3", Thread.currentThread());
                    exchange.getMessage().setBody(logs);
                })
                .log("after parallel flow 3 : ${body}")
                .end();

    }
}

