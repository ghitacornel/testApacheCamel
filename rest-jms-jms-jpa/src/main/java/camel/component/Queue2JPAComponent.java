package camel.component;

import camel.model.CustomMessage;
import camel.repository.MessageDbRepository;
import camel.repository.entity.MessageDb;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Queue2JPAComponent implements Processor {

    private final MessageDbRepository repository;

    @Override
    public void process(Exchange exchange) {
        CustomMessage customMessage = exchange.getIn().getBody(CustomMessage.class);
        customMessage.getLog().add("Queue2JPA processed");
        repository.save(
                MessageDb.builder()
                        .id(customMessage.getId())
                        .logs(String.valueOf(customMessage.getLog()))
                        .build()
        );
    }
}
