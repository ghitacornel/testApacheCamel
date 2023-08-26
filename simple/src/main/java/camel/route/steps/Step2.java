package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class Step2 implements Processor {
    @Override
    public void process(Exchange exchange) {
        Long bodyLong = exchange.getIn().getBody(Long.class);
        String body = bodyLong + " 3";
        exchange.getMessage().setBody(body);
    }
}
