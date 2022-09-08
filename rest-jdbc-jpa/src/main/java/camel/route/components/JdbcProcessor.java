package camel.route.components;

import camel.route.model.PersonRequest;
import camel.route.model.PersonResponse;
import camel.route.repository.PersonJDBCRepository;
import camel.route.repository.entity.Person;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JdbcProcessor implements Processor {

    private final PersonJDBCRepository personJDBCRepository;

    @Override
    public void process(Exchange exchange) {
        PersonRequest request = exchange.getIn().getBody(PersonRequest.class);
        Person person = Person.builder()
                .id(request.getId())
                .name(request.getName())
                .age(request.getAge())
                .build();
        personJDBCRepository.insert(person);
        PersonResponse response = new PersonResponse(person.getId(), person.getName(), person.getAge());
        exchange.getMessage().setBody(response);
    }

}
