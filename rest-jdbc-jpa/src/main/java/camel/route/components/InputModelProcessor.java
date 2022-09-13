package camel.route.components;

import camel.route.model.PersonRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class InputModelProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        PersonRequest personRequest = exchange.getIn().getBody(PersonRequest.class);
        if (personRequest.getProcessor().startsWith("jdbc")) {
            exchange.getMessage().setHeader("system", "jdbc");
        }
        if (personRequest.getProcessor().startsWith("jpa")) {
            exchange.getMessage().setHeader("system", "jpa");
        }
    }

}
