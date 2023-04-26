package camel.test;

import camel.database.Order;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping(value = "payment")
public class ExternalPaymentREST {

    private final Random random = new Random();

    @PutMapping
    public void pay(@RequestBody Order order) {

        {// simulate error here
            int i = random.nextInt(100);
            if (i > 50) {
                throw new SimulatorException("simulate error for payment, order id = " + order.getId());
            }
        }

    }
}
