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
    private final ReadTryForVoucherOrdersFromDBProcessor readTryForVoucherOrdersFromDBProcessor;
    private final ReadTryForPaymentOrdersFromDBProcessor readTryForPaymentOrdersFromDBProcessor;
    private final CallVoucherProcessor callVoucherProcessor;
    private final CallPaymentProcessor paymentProcessor;
    private final CompleteOrderProcessor completeOrderProcessor;
    private final DeleteCompletedOrdersProcessor deleteCompletedOrdersProcessor;

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
                .process(readTryForVoucherOrdersFromDBProcessor)
                .log("TRY_FOR_VOUCHER orders ${body}")
                .split(bodyAs(List.class))
                .parallelProcessing(true)
                .to("direct:voucher")
        ;

        from("timer://simpleTimer?period=4500")
                .routeId("payment-retry-route")
                .process(readTryForPaymentOrdersFromDBProcessor)
                .log("TRY_FOR_PAYMENT orders ${body}")
                .split(bodyAs(List.class))
                .parallelProcessing(true)
                .to("direct:voucher")
        ;

        from("direct:voucher")
                .process(callVoucherProcessor)
                .process(completeOrderProcessor)
                .process(paymentProcessor)
                .end()
        ;

        from("timer://simpleTimer?period=10000")
                .routeId("delete-completed-orders-route")
                .process(deleteCompletedOrdersProcessor)
                .end()
        ;
    }
}

