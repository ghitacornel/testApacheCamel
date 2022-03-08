package camel.route;

import camel.route.steps.FinalStep;
import camel.route.steps.InitialStep;
import camel.route.steps.Step2;
import camel.route.steps.Step3;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Route extends RouteBuilder {

    private final InitialStep initialStep;
    private final Step2 step2;
    private final Step3 step3;
    private final FinalStep finalStep;

    @Override
    public void configure() {
        from("direct:start")
                .routeId("simple-route")
                .log("start of the route")
                .bean(initialStep)
                .log("before step 2 body = " + body().toString())
                .process(step2)
                .log("after step 2 body = " + body().toString())
                .log("before step 3 body = " + body().toString())
                .process(step3)
                .log("after step 3 body = " + body().toString())
                .bean(finalStep)
                .log("end of the route")
        ;
    }
}

