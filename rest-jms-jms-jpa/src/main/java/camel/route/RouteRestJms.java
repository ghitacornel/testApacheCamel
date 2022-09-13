package camel.route;

import camel.component.RestJmsComponent;
import camel.model.CustomMessage;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class RouteRestJms extends RouteBuilder {

    private final RestJmsComponent restJmsComponent;

    @Override
    public void configure() {

        // This section is required - it tells Camel how to configure the REST service
        restConfiguration()
                // Use the 'servlet' component.
                // This tells Camel to create and use a Servlet to 'host' the RESTful API.
                // Since we're using Spring Boot, the default servlet container is Tomcat.
                .component("servlet")
                .contextPath("/")

                // Allow Camel to try to marshal/unmarshal between Java objects and JSON
                .bindingMode(RestBindingMode.auto);


        // Now define the REST API (POST, GET, etc.)
        rest()
                .path("/jms") // This makes the API available at http://host:port/$CONTEXT_ROOT/jms

                // HTTP: POST /api
                .post()
                .consumes(APPLICATION_JSON_VALUE)
                .type(CustomMessage.class)
                .outType(Void.class)
                .to("direct:post");

        from("direct:post")
                .bean(restJmsComponent)
                .log("${body}")
                .to("jms:queue:FirstQueue")
                .end();

        from("jms:queue:FirstQueue")
                .log("${body}")
                .to("jms:queue:SecondQueue")
                .end();

        from("jms:queue:SecondQueue")
                .log("${body}")
                .end();

    }

}
