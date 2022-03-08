package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class Step3 implements Processor {
    @Override
    public void process(Exchange exchange) {
        String body = exchange.getIn().getBody(String.class);
        body += " 3";
        exchange.getMessage().setBody(body);
    }
}
