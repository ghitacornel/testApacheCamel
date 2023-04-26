package camel.route.steps;

import camel.database.Order;
import camel.database.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteCompletedOrdersProcessor implements Processor {

    private final OrderRepository orderRepository;


    @Override
    public void process(Exchange exchange) throws Exception {
        List<Order> orders = orderRepository.findCompletedOrders();
        log.info("delete completed orders " + orders.stream().map(Order::getId).toList());
        orderRepository.deleteAll(orders);
    }
}
