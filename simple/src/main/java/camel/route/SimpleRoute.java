package camel.route;

import camel.route.steps.FinalStep;
import camel.route.steps.InitialStep;
import camel.route.steps.Step1;
import camel.route.steps.Step2;
import org.apache.camel.builder.RouteBuilder;

public class SimpleRoute extends RouteBuilder {

    private final InitialStep initialStep = new InitialStep();
    private final Step1 step1 = new Step1();
    private final Step2 step2 = new Step2();
    private final FinalStep finalStep = new FinalStep();

    @Override
    public void configure() {
        from("direct:start")
                .routeId("simple-route")
                .log("start of the route")
                .bean(initialStep)
                .log("before step 1 body = " + body().toString())
                .process(step1)
                .log("after step 1 body = " + body().toString())
                .log("before step 2 body = " + body().toString())
                .process(step2)
                .log("after step 2 body = " + body().toString())
                .bean(finalStep)
                .log("end of the route")
        ;
    }
}

