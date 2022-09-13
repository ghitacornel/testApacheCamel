package camel.route;

import camel.component.Queue1Queue2Component;
import camel.component.Queue2JPAComponent;
import camel.component.RestJmsComponent;
import camel.model.CustomMessage;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class RouteRestJms extends RouteBuilder {

    private final RestJmsComponent restJmsComponent;
    private final Queue1Queue2Component queue1Queue2Component;
    private final Queue2JPAComponent queue2JPAComponent;

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

        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(CustomMessage.class);

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
                .marshal().json()
                .log("from rest to queue : ${body}")
                .to("jms:queue:FirstQueue")
                .end();

        from("jms:queue:FirstQueue")
                .unmarshal(jsonDataFormat)
                .bean(queue1Queue2Component)
                .marshal().json()
                .log("from queue to queue : ${body}")
                .to("jms:queue:SecondQueue")
                .end();

        from("jms:queue:SecondQueue")
                .unmarshal(jsonDataFormat)
                .bean(queue2JPAComponent)
                .log("from queue to jpa : ${body}")
                .end();

    }

}
