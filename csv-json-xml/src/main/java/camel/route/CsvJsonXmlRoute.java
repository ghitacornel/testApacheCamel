package camel.route;

import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CsvJsonXmlRoute extends RouteBuilder {

    @Value("${route.input.path}")
    private String inputPath;

    @Value("${route.input.file}")
    private String inputFile;

    @Value("${route.output.path}")
    private String outputPath;

    @Value("${route.output.file}")
    private String outputFile;

    @Value("${route.output.xml.path}")
    private String outputXmlPath;

    @Value("${route.output.xml.file}")
    private String outputXmlFile;

    @Override
    public void configure() {
        from(StaticEndpointBuilders.file(inputPath).fileName(inputFile).delete(true))
                .log("processing csv file \n\n ${body} \n ")
                .unmarshal()
                .bindy(BindyType.Csv, CsvRow.class)
                .split()
                .body()
                .log("processing csv item ${body}")
                .filter(exchange -> {
                    Object body = exchange.getIn().getBody();
                    CsvRow input = (CsvRow) body;
                    return input.getAge() > 0;
                })
                .filter(exchange -> {
                    Object body = exchange.getIn().getBody();
                    CsvRow input = (CsvRow) body;
                    return input.getId() < 200;
                })
                .process(exchange -> {
                    Object body = exchange.getIn().getBody();
                    CsvRow input = (CsvRow) body;
                    input.setName(input.getName().toUpperCase());
                })
                .process(exchange -> {
                    Object body = exchange.getIn().getBody();
                    CsvRow input = (CsvRow) body;
                    input.setAge(input.getAge() + 1);
                })
                .marshal()
                .json()
                .log("processing json item ${body}")
                .aggregate(constant(true), AggregationStrategies.string(","))
                .completionSize(3)
                .transform(simple("[${body}]"))
                .to(StaticEndpointBuilders.file(outputPath).fileName(outputFile).fileExist("Append"))
                .log("json file completed \n\n ${body} \n ")
                .log("processing json file \n\n ${body} \n ")
                .unmarshal(new ListJacksonDataFormat(JsonRow.class))
                .split()
                .body()
                .log("processing json item ${body}")
                .filter(exchange -> {
                    Object body = exchange.getIn().getBody();
                    JsonRow input = (JsonRow) body;
                    return input.getId() < 3;
                })
                .process(exchange -> {
                    Object body = exchange.getIn().getBody();
                    JsonRow input = (JsonRow) body;
                    input.setName(StringUtils.capitalize(input.getName().toLowerCase()));
                })
                .marshal()
                .jacksonXml()
                .log("processing xml item ${body}")
                .aggregate(constant(true), AggregationStrategies.string("\n"))
                .completionSize(2)
                .transform(simple("<alldata>\n${body}\n</alldata>"))
                .to(StaticEndpointBuilders.file(outputXmlPath).fileName(outputXmlFile).fileExist("Override"))
                .log("xml file completed \n\n ${body} \n ")
        ;
    }
}

