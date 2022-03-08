package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Step2 implements Processor {
    @Override
    public void process(Exchange exchange) {
        Date bodyDate = exchange.getIn().getBody(Date.class);
        Long body = bodyDate.getTime() + 222;
        exchange.getMessage().setBody(body);
    }
}
