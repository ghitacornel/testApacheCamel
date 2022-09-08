package camel.route.repository;

import camel.route.repository.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    public void insert(Person person) {
        jdbcTemplate.update("INSERT INTO person(id,name,age) VALUES (?,?,?)", person.getId(), person.getName(), person.getAge());
    }
}
