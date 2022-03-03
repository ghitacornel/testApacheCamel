package camel.route1;

import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Route1 extends RouteBuilder {

    @Value("${route1.input}")
    private String input;

    @Value("${route1.output}")
    private String output;

    @Override
    public void configure() {
        from(input)
                .unmarshal()
                .bindy(BindyType.Csv, CsvInput.class)
                .split()
                .body()
                .log("processing csv item ${body}")
                .filter(exchange -> {
                    Object body = exchange.getIn().getBody();
                    CsvInput input = (CsvInput) body;
                    return input.getAge() > 0;
                })
                .filter(exchange -> {
                    Object body = exchange.getIn().getBody();
                    CsvInput input = (CsvInput) body;
                    return input.getId() < 200;
                })
                .process(exchange -> {
                    Object body = exchange.getIn().getBody();
                    CsvInput input = (CsvInput) body;
                    input.setName(input.getName().toUpperCase());
                })
                .process(exchange -> {
                    Object body = exchange.getIn().getBody();
                    CsvInput input = (CsvInput) body;
                    input.setAge(input.getAge() / 10);
                })
                .marshal()
                .json()
                .log("processing json item ${body}")
                .aggregate(constant(true), AggregationStrategies.string(","))
                .completionSize(3)
                .log("processing json concat ${body}")
                .to(output)
        ;
    }
}

