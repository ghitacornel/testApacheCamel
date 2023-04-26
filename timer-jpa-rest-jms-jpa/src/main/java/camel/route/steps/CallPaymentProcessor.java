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
public class CallPaymentProcessor implements Processor {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    // @Transactional no transactional here => multiple steps with possible failing conditions
    @Override
    public void process(Exchange exchange) throws Exception {

        // step 1
        Order order = exchange.getMessage().getBody(Order.class);
        order.setStatus(OrderStatus.TRY_FOR_PAYMENT);
        orderRepository.save(order);

        // step 2
        try {
            restTemplate.put("http://localhost:8080/payment", order);
        } catch (Exception e) {
            order.setPaymentTryCount(order.getPaymentTryCount() + 1);
            if (order.getPaymentTryCount() > 3) {
                order.setStatus(OrderStatus.FAILED);
                orderRepository.save(order);
                log.error("Order marked as permanently failed due to payment " + order.getId() + " tryouts " + order.getPaymentTryCount());
            } else {
                orderRepository.save(order);
                log.error("Error paying order " + order.getId() + " tryout " + order.getPaymentTryCount());
            }
            return;
        }

        // step 3
        order.setStatus(OrderStatus.PAYMENT_COMPLETE);
        orderRepository.save(order);
        log.info("Order " + order.getId() + "  was paid");
    }
}
