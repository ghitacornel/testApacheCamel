package camel.route.steps;

import camel.database.Order;
import camel.database.OrderRepository;
import camel.database.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CompleteOrderProcessor implements Processor {

    private final OrderRepository orderRepository;

    @Override
    public void process(Exchange exchange) throws Exception {
        Order order = exchange.getMessage().getBody(Order.class);

        // do not complete orders with status different than this
        if (order.getStatus() != OrderStatus.PAYMENT_COMPLETED) {
            return;
        }

        order.setStatus(OrderStatus.PROCESSED);
        orderRepository.save(order);
        log.info("order completed " + order.getId() + " with tryouts " + order.getPercentageVoucherReductionTryCount());
    }
}
