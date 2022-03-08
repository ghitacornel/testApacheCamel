package camel.route.components;

import camel.route.model.OutputModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutputModelProcessor implements Processor {

    private final ObjectMapper objectMapper;

    @Override
    public void process(Exchange exchange) throws Exception {
        OutputModel outputModel = exchange.getIn().getBody(OutputModel.class);
        exchange.getMessage().setBody(objectMapper.writeValueAsString(outputModel));
    }

}
