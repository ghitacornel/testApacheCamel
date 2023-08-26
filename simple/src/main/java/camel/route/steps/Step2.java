package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.time.LocalDate;

public class Step2 implements Processor {
    @Override
    public void process(Exchange exchange) {
        LocalDate body = exchange.getIn().getBody(LocalDate.class);
        exchange.getMessage().setBody(body.plusDays(1).toString());
    }
}
