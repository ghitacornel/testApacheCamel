package camel.component;

import camel.model.CustomMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class RestJmsComponent implements Processor {

    @Override
    public void process(Exchange exchange) {
        CustomMessage customMessage = exchange.getIn().getBody(CustomMessage.class);
        customMessage.getLog().add("RestJmsComponent processed");
        exchange.getMessage().setBody(customMessage);
    }
}
