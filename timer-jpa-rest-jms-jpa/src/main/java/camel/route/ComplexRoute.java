package camel.route;

import camel.route.steps.*;
import camel.route.steps.ReadNewOrdersFromDBProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ComplexRoute extends RouteBuilder {

    private final ReadNewOrdersFromDBProcessor readNewOrdersFromDBProcessor;
    private final ReadTryForVoucherPercentageOrdersFromDBProcessor readTryForVoucherPercentageOrdersFromDBProcessor;
    private final CallVoucherForPercentageProcessor callVoucherForPercentageProcessor;
    private final CompleteOrderProcessor completeOrderProcessor;

    @Override
    public void configure() {
        from("timer://simpleTimer?period=3000")
                .routeId("start-route")
                .log("start of the route for NEW orders")
                .process(readNewOrdersFromDBProcessor)
                .log("NEW orders to be processed ${body}")
                .split(bodyAs(List.class))
                .parallelProcessing(true)
                .to("direct:voucher")
        ;

        from("timer://simpleTimer?period=4500")
                .routeId("voucher-retry-route")
                .log("start of the route for TRY_FOR_VOUCHER_PERCENTAGE orders")
                .process(readTryForVoucherPercentageOrdersFromDBProcessor)
                .log("NEW orders to be processed ${body}")
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

