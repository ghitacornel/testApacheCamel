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
public class CallVoucherProcessor implements Processor {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    // @Transactional no transactional here => multiple steps with possible failing conditions
    @Override
    public void process(Exchange exchange) throws Exception {

        // step 1
        Order order = exchange.getMessage().getBody(Order.class);
        order.setStatus(OrderStatus.TRY_FOR_VOUCHER);
        orderRepository.save(order);

        // step 2
        ResponseEntity<Integer> response;
        try {
            response = restTemplate.getForEntity("http://localhost:8080/voucher/{id}", Integer.class, order.getId());
        } catch (Exception e) {
            order.setVoucherTryCount(order.getVoucherTryCount() + 1);
            if (order.getVoucherTryCount() > 3) {
                order.setStatus(OrderStatus.FAILED);
                orderRepository.save(order);
                log.error("FAIL Order due to voucher" + order.getId() + " tryout " + order.getVoucherTryCount());
            } else {
                orderRepository.save(order);
                log.error("Voucher call error, order " + order.getId() + " tryout " + order.getVoucherTryCount());
            }
            throw e;
        }

        // step 3
        order.setPercentageVoucherReduction(response.getBody());
        order.setStatus(OrderStatus.VOUCHER_COMPLETED);
        orderRepository.save(order);
        log.info("Order " + order.getId() + "  has " + order.getPercentageVoucherReduction() + "% reduction");
    }
}
