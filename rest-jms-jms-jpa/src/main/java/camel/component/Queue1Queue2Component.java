package camel.component;

import camel.model.CustomMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class Queue1Queue2Component implements Processor {

    @Override
    public void process(Exchange exchange) {
        CustomMessage customMessage = exchange.getIn().getBody(CustomMessage.class);
        customMessage.getLog().add("Queue1Queue2 processed");
        exchange.getMessage().setBody(customMessage);
    }
}
