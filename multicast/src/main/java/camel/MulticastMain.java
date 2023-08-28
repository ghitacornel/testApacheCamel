package camel;

import camel.route.MulticastParallelRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class MulticastMain {

    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new MulticastParallelRoute());
            camelContext.start();
            {
                // nothing happens till we don't initiate a startup message
                // initiate a startup message
                // can be a dummy one
                // observe random execution order
                camelContext.createProducerTemplate().sendBody("direct:start", "This is a dummy startup message");
            }

            {
                // nothing happens till we don't initiate a startup message
                // initiate a startup message
                // can be a dummy one
                // observe random execution order
                camelContext.createProducerTemplate().sendBody("direct:start", "This is a dummy startup message");
            }

        }
    }
}
