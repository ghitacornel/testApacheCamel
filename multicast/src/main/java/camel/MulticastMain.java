package camel;

import camel.route.MulticastParallelRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.Serializable;

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
                // observe same body is send to all, not a copy or a clone
                camelContext.createProducerTemplate().sendBody("direct:start", new MyBean(1,2));
            }

            {
                // nothing happens till we don't initiate a startup message
                // initiate a startup message
                // can be a dummy one
                // observe random execution order
                // observe same body is send to all, not a copy or a clone
                camelContext.createProducerTemplate().sendBody("direct:start", new MyBean(2,3));
            }

        }
    }
}
