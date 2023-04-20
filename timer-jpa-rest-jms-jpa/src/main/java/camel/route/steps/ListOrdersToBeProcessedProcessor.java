package camel.route.steps;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ListOrdersToBeProcessedProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        log.info(String.valueOf(exchange.getMessage().getBody(List.class)));
    }
}
