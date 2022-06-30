package camel.route;

import camel.route.components.*;
import camel.route.model.InputModel;
import camel.route.model.OutputModel;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class RestJdbcJpaRoute extends RouteBuilder {

    private final JdbcProcessor jdbcProcessor;
    private final JpaProcessor jpaProcessor;
    private final NoProcessor noProcessor;
    private final InputModelProcessor inputModelProcessor;
    private final OutputModelProcessor outputModelProcessor;

    @Override
    public void configure() {

        // This section is required - it tells Camel how to configure the REST service
        restConfiguration()
                // Use the 'servlet' component.
                // This tells Camel to create and use a Servlet to 'host' the RESTful API.
                // Since we're using Spring Boot, the default servlet container is Tomcat.
                .component("servlet")

                // Allow Camel to try to marshal/unmarshal between Java objects and JSON
                .bindingMode(RestBindingMode.auto);


        // Now define the REST API (POST, GET, etc.)
        rest()
                .path("/api") // This makes the API available at http://host:port/$CONTEXT_ROOT/api

                .consumes(APPLICATION_JSON_VALUE)
                .produces(APPLICATION_JSON_VALUE)

                // HTTP: GET /api
                .get()
                .produces("application/text")
                .to("direct:simple")

                // HTTP: POST /api
                .post()
                .consumes("application/json")
                .type(InputModel.class)
                .produces(APPLICATION_JSON_VALUE)
                .outType(OutputModel.class)
                .to("direct:modelProcessor");

        from("direct:simple")
                .transform()
                .constant("simple response");

        from("direct:modelProcessor")
                .bean(inputModelProcessor)
                .choice()
                .when(header("system").isEqualTo("jpa")).bean(jpaProcessor)
                .when(header("system").isEqualTo("jdbc")).bean(jdbcProcessor)
                .otherwise()
                .bean(noProcessor)
                .end()
                .bean(outputModelProcessor)
                .end();

    }

}
