package camel.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping(value = "voucher")
public class ExternalVoucherREST {

    private final Random random = new Random();

    @GetMapping(value = "{id}")
    public Integer getPercentage(@PathVariable(name = "id") Integer id) {

        {// simulate error here
            int i = random.nextInt(100);
            if (i > 50) {
                throw new SimulatorException("simulate error for voucher, order id = " + id);
            }
        }

        return random.nextInt(100);
    }
}
