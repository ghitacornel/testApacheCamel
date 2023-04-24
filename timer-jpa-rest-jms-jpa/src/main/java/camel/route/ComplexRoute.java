package camel.route;

import camel.route.steps.CallVoucherForPercentageProcessor;
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
    private final CallVoucherForPercentageProcessor callVoucherForPercentageProcessor;
    private final CompleteOrderProcessor completeOrderProcessor;

    @Override
    public void configure() {
        from("timer://simpleTimer?period=3000")
                .routeId("simple-route")
                .log("start of the route at ${body}")
                .process(readBulkFromDBProcessor)
                .log("orders to be processed ${body}")
                .split(bodyAs(List.class))
                .parallelProcessing(true)
                .process(callVoucherForPercentageProcessor)
                .process(completeOrderProcessor)
                .end()
                .log("end of the route")
        ;
    }
}

