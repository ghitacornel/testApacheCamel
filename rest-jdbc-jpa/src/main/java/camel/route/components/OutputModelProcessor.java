package camel.route.components;

import camel.route.model.PersonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutputModelProcessor implements Processor {

    private final ObjectMapper objectMapper;

    @Override
    public void process(Exchange exchange) throws Exception {
        PersonResponse personResponse = exchange.getIn().getBody(PersonResponse.class);
        exchange.getMessage().setBody(objectMapper.writeValueAsString(personResponse));
    }

}
