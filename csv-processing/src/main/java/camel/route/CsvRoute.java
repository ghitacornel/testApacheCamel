package camel.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CsvRoute extends RouteBuilder {

    @Value("${route.input.path}")
    private String inputPath;

    @Value("${route.input.file}")
    private String inputFile;

    @Override
    public void configure() {
        String uri = "file:" + inputPath + "?fileName=" + inputFile + "&noop=true";
        log.info(uri);
        from(uri)
                .log("processing csv file \n\n ${body} \n ")
                .unmarshal()
                .bindy(BindyType.Csv, CsvRow.class)
                // split the message into other messages
                // 1 file is considered the initial message
                // which is split into multiple messages using policy : 1 message per row
                .split()
                .body()
                .log("result row ${body}")
                .end();

    }
}

