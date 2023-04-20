package camel.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComplexRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:start")
                .routeId("simple-route")
                .log("start of the route")
                .log("end of the route")
        ;
    }
}

