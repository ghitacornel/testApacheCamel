package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Date;

public class InitialStep implements Processor {

    @Override
    public void process(Exchange exchange) {
        exchange.getMessage().setBody(exchange.getMessage().getBody(String.class) + "_InitialStep");
    }
}
