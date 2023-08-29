package camel;

import camel.route.LoopRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class LoopMain {

    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new LoopRoute());
            camelContext.start();

            // nothing happens till we don't initiate a startup message
            // initiate a startup message
            camelContext.createProducerTemplate().sendBody("direct:start1", "initial");
            camelContext.createProducerTemplate().sendBody("direct:start2", "initial");

        }
    }
}
