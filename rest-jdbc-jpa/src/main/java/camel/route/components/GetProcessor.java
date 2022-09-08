package camel.route.components;

import camel.route.model.PersonResponse;
import camel.route.repository.PersonRepository;
import camel.route.repository.entity.Person;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProcessor implements Processor {

    private final PersonRepository personRepository;

    @Override
    public void process(Exchange exchange) {
        Integer id = exchange.getIn().getHeader("id", Integer.class);
        Person person = personRepository.findById(id).get();
        PersonResponse response = new PersonResponse(person.getId(), person.getName(), person.getAge());
        exchange.getMessage().setBody(response);
    }
}
