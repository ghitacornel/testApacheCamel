package camel;

import camel.route.SplitRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class SplitMain {
    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new SplitRoute());
            camelContext.start();

            // nothing happens till we don't initiate a startup message
            // initiate a startup message
            camelContext.createProducerTemplate().sendBody("direct:start", "1,2,3");

        }
    }
}