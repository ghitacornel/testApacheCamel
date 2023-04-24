package camel.route.steps;

import camel.database.Order;
import camel.database.OrderRepository;
import camel.database.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallVoucherForPercentageProcessor implements Processor {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    // @Transactional no transactional here
    @Override
    public void process(Exchange exchange) throws Exception {
        Order order = exchange.getMessage().getBody(Order.class);
        order.setStatus(OrderStatus.VOUCHER_PERCENTAGE);
        orderRepository.save(order);

        ResponseEntity<Integer> response = restTemplate.getForEntity("http://localhost:8080/voucher/{id}", Integer.class, order.getId());

        order.setPercentageVoucherReduction(response.getBody());
        order.setStatus(OrderStatus.VOUCHER_PERCENTAGE_COMPLETED);
        orderRepository.save(order);
        log.info("order " + order.getId() + "  has " + order.getPercentageVoucherReduction() + "% reduction");
    }
}
