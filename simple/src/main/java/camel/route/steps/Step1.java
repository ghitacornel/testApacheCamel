package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Date;

public class Step1 implements Processor {
    @Override
    public void process(Exchange exchange) {
        Date bodyDate = exchange.getIn().getBody(Date.class);
        Long body = bodyDate.getTime() + 222;
        exchange.getMessage().setBody(body);
    }
}
