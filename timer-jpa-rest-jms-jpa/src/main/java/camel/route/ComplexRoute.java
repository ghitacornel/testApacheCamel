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
    private final CallPaymentProcessor callPaymentProcessor;
    private final CompleteOrderProcessor completeOrderProcessor;
    private final DeleteCompletedOrdersProcessor deleteCompletedOrdersProcessor;

    @Override
    public void configure() {
        from("timer://simpleTimer?period=3000")
                .routeId("start-route")
                .process(readNewOrdersFromDBProcessor)
                .log("NEW orders to be processed ${body}")
                .split(bodyAs(List.class))
//                .parallelProcessing(true)
                .to("direct:voucher")
        ;

        from("timer://simpleTimer?period=4500")
                .routeId("voucher-retry-route")
                .process(readTryForVoucherOrdersFromDBProcessor)
                .log("TRY_FOR_VOUCHER orders ${body}")
                .split(bodyAs(List.class))
//                .parallelProcessing(true)
                .to("direct:voucher")
        ;

        from("timer://simpleTimer?period=4500")
                .routeId("payment-retry-route")
                .process(readTryForPaymentOrdersFromDBProcessor)
                .log("TRY_FOR_PAYMENT orders ${body}")
                .split(bodyAs(List.class))
//                .parallelProcessing(true)
                .to("direct:voucher")
        ;

        from("direct:voucher")
                .process(callVoucherProcessor)
                .process(callPaymentProcessor)
                .process(completeOrderProcessor)
                .choice()
                .when(simple("${body.status.name()} == 'PROCESSED'"))
                .marshal().json()
                .log("send to complete jms queue ${body}")
                .to("jms:queue:CompletedOrdersQueueName")
                .when(simple("${body.status.name()} == 'FAILED'"))
                .marshal().json()
                .log("send to failed jms queue ${body}")
                .to("jms:queue:FailedOrdersQueueName")
                .otherwise()
                .end()
                .end()
        ;

        from("timer://simpleTimer?period=10000")
                .routeId("delete-completed-orders-route")
                .process(deleteCompletedOrdersProcessor)
                .end()
        ;

        from("jms:queue:CompletedOrdersQueueName")
                .log("got completed from jms ${body}")
                .end();
        from("jms:queue:FailedOrdersQueueName")
                .log("got failed from jms ${body}")
                .end();
    }
}

