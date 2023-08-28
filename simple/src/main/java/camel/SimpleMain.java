package camel;

import camel.route.SimpleRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class SimpleMain {

    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new SimpleRoute());
            camelContext.start();

            {
                // nothing happens till we don't initiate a startup message
                // initiate a startup message
                // can be a dummy one
                camelContext.createProducerTemplate().sendBody("direct:start", "This is a dummy startup message");
            }

            {
                // nothing happens till we don't initiate a startup message
                // initiate a startup message
                // can be a dummy one
                camelContext.createProducerTemplate().sendBody("direct:start", "This is a dummy startup message");
            }

            camelContext.stop();
        }

    }
}
