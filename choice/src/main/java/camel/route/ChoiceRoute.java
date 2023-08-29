package camel.route;

import org.apache.camel.builder.RouteBuilder;

public class ChoiceRoute extends RouteBuilder {
    @Override
    public void configure() {

        from("direct:start")
                .routeId("simple-choice")
                .choice()
                .when(simple("${body} == 'one'"))
                .log("first option flow")
                .to("direct:choiceOne")
                .when(simple("${body} == 'two'"))
                .log("second option flow")
                .to("direct:choiceTwo")
                .otherwise()
                .log("default option flow")
                .to("direct:default");

        from("direct:choiceOne").log("${body}").end();
        from("direct:choiceTwo").log("${body}").end();
        from("direct:default").log("${body}").end();
    }
}
