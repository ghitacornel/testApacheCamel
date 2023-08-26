package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FinalStep implements Processor {


    @Override
    public void process(Exchange exchange) {
        exchange.getMessage().setBody(exchange.getMessage(String.class) + "_FinalStep");
    }
}
