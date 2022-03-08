package camel.route.components;

import camel.route.model.InputModel;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class InputModelProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        InputModel inputModel = exchange.getIn().getBody(InputModel.class);
        if (inputModel.getName().startsWith("jdbc")) {
            exchange.getMessage().setHeader("system", "jdbc");
        }
        if (inputModel.getName().startsWith("jpa")) {
            exchange.getMessage().setHeader("system", "jpa");
        }
        exchange.getMessage().setBody(inputModel);
    }

}
