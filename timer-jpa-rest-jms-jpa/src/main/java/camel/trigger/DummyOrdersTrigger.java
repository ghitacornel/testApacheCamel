package camel.trigger;

import camel.database.Order;
import camel.database.OrderRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyOrdersTrigger {

    private final OrderRepository orderRepository;

    private static final Faker faker = new Faker();

    @Scheduled(fixedRate = 100)
    public void addNewOrder() {
        Order order = Order.builder()
                .product(faker.commerce().productName())
                .price(faker.random().nextInt(10, 100))
                .quantity(faker.random().nextInt(1, 1000))
                .build();
        orderRepository.save(order);
    }

}
