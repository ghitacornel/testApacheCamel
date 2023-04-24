package camel.route;

import camel.route.steps.CallVoucherForPercentageProcessor;
import camel.route.steps.CompleteOrderProcessor;
import camel.route.steps.Read_NEW_FromDBProcessor;
import camel.route.steps.Read_VOUCHER_PERCENTAGE_FromDBProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ComplexRoute extends RouteBuilder {

    private final Read_NEW_FromDBProcessor readNEWFromDBProcessor;
    private final Read_VOUCHER_PERCENTAGE_FromDBProcessor readVoucherPercentageFromDBProcessor;
    private final CallVoucherForPercentageProcessor callVoucherForPercentageProcessor;
    private final CompleteOrderProcessor completeOrderProcessor;

    @Override
    public void configure() {
        from("timer://simpleTimer?period=3000")
                .routeId("start-route")
                .log("start of the route for NEW orders")
                .process(readNEWFromDBProcessor)
                .log("NEW orders to be processed ${body}")
                .split(bodyAs(List.class))
                .parallelProcessing(true)
                .to("direct:voucher")
        ;

        from("timer://simpleTimer?period=5000")
                .routeId("voucher-route")
                .log("start of the route for VOUCHER_PERCENTAGE orders")
                .process(readVoucherPercentageFromDBProcessor)
                .log("VOUCHER_PERCENTAGE orders to be processed ${body}")
                .split(bodyAs(List.class))
                .parallelProcessing(true)
                .to("direct:voucher")
        ;

        from("direct:voucher")
                .process(callVoucherForPercentageProcessor)
                .process(completeOrderProcessor)
                .end()
        ;
    }
}

