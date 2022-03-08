package camel.route.steps;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitialStep implements Processor {

    public Date testDate;

    @Override
    public void process(Exchange exchange) {
        testDate = new Date();
        exchange.getMessage().setBody(testDate);
    }
}
