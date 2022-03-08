package camel.route.components;

import camel.route.model.InputModel;
import camel.route.model.OutputModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class NoProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        InputModel inputModel = exchange.getIn().getBody(InputModel.class);
        OutputModel outputModel = new OutputModel("no_id", inputModel.getName());
        exchange.getMessage().setBody(outputModel);
    }

}
