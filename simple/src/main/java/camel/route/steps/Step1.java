package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.time.LocalDate;
import java.util.Date;

public class Step1 implements Processor {
    @Override
    public void process(Exchange exchange) {
        exchange.getMessage().setBody(LocalDate.of(2023, 8, 26));
    }
}
