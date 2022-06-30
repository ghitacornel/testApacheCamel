package camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ParallelRoute extends RouteBuilder {

    @Value("${route.input.path}")
    private String inputPath;

    @Value("${route.input.file}")
    private String inputFile;

    @Value("${route.output.path}")
    private String outputPath;

    @Value("${route.output.file}")
    private String outputFile;

    @Override
    public void configure() {
        // TODO make it parallel later
        from(StaticEndpointBuilders.file(inputPath).fileName(inputFile).delete(true))
                .log("processing file \n\n ${body} \n ")
                .to(StaticEndpointBuilders.file(outputPath).fileName(outputFile).fileExist("Override"))
                .log("file completed \n\n ${body} \n ")
        ;
    }
}

