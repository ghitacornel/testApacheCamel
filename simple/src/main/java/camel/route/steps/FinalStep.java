package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class FinalStep implements Processor {

    public String testString;

    @Override
    public void process(Exchange exchange) {
        testString = exchange.getIn().getBody(String.class);
    }
}
