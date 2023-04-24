package camel.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping(value = "voucher")
public class ExternalVoucherService {

    private final Random random = new Random();

    @GetMapping(value = "{id}")
    public Integer getPercentage() {
        int i = random.nextInt();
        if (i > 80) {
            throw new RuntimeException("simulate error");
        }
        return random.nextInt(100);
    }
}
