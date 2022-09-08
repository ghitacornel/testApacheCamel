package camel.route.components;

import camel.route.model.PersonRequest;
import camel.route.model.PersonResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class NoProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        PersonRequest personRequest = exchange.getIn().getBody(PersonRequest.class);
        PersonResponse personResponse = new PersonResponse(null, personRequest.getName(), personRequest.getAge());
        exchange.getMessage().setBody(personResponse);
    }

}
