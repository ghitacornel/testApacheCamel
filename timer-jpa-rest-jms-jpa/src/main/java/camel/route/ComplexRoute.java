package camel.route;

import camel.route.steps.ListOrdersToBeProcessedProcessor;
import camel.route.steps.ReadBulkFromDBProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComplexRoute extends RouteBuilder {

    private final ReadBulkFromDBProcessor readBulkFromDBProcessor;
    private final ListOrdersToBeProcessedProcessor ordersToBeProcessedProcessor;

    @Override
    public void configure() {
        from("direct:start")
                .routeId("simple-route")
                .log("start of the route at ${body}")
                .process(readBulkFromDBProcessor)
                .process(ordersToBeProcessedProcessor)
                .log("end of the route")
        ;
    }
}

