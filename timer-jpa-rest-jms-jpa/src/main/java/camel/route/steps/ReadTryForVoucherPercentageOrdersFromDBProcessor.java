package camel.route.steps;

import camel.database.Order;
import camel.database.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReadTryForVoucherPercentageOrdersFromDBProcessor implements Processor {

    private final OrderRepository orderRepository;

    @Override
    public void process(Exchange exchange) throws Exception {
        List<Order> orders = orderRepository.findTryForVoucherPercentageOrders();
        exchange.getMessage().setBody(orders);
    }
}
