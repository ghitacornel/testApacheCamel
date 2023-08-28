package camel;

import camel.route.ChoiceRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class ChoiceMain {

    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new ChoiceRoute());
            camelContext.start();
            {
                // nothing happens till we don't initiate a startup message
                // initiate a startup message
                camelContext.createProducerTemplate().sendBody("direct:start", "one");
            }

            {
                // nothing happens till we don't initiate a startup message
                // initiate a startup message
                camelContext.createProducerTemplate().sendBody("direct:start", "two");
            }

            {
                // nothing happens till we don't initiate a startup message
                // initiate a startup message
                camelContext.createProducerTemplate().sendBody("direct:start", "something else");
            }

        }
    }
}
