package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class InitialStep implements Processor {

    public String testString;

    @Override
    public void process(Exchange exchange) {
        testString = "input data";
        exchange.getMessage().setBody(testString);
    }
}
