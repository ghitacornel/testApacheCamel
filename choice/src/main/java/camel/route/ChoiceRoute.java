package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class ChoiceRoute extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:start")
                .routeId("simple-choice")
                .choice()
                .when(simple("${body} == 'one'"))
                .to("direct:a")
                .when(simple("${body} == 'two'"))
                .to("direct:b")
                .otherwise()
                .to("direct:c");

        from("direct:a").log("${body}").end();
        from("direct:b").log("${body}").end();
        from("direct:c").log("${body}").end();
    }
}
