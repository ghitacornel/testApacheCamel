package camel.route;

import camel.route.steps.CompleteOrderProcessor;
import camel.route.steps.ReadBulkFromDBProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ComplexRoute extends RouteBuilder {

    private final ReadBulkFromDBProcessor readBulkFromDBProcessor;
    private final CompleteOrderProcessor completeOrderProcessor;

    @Override
    public void configure() {
        from("direct:start")
                .routeId("simple-route")
                .log("start of the route at ${body}")
                .process(readBulkFromDBProcessor)
                .log("orders to be processed ${body}")
                .split(bodyAs(List.class))
                .parallelProcessing(true)
                .log("order to be processed ${body}")
                .process(completeOrderProcessor)
                .log("end of the route")
        ;
    }
}

