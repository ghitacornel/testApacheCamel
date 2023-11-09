package camel;

import camel.route.SplitAndAggregateRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class SplitAndAggregateMain {
    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new SplitAndAggregateRoute());
            camelContext.start();

            // nothing happens till we don't initiate a startup message
            // initiate a startup message
            camelContext.createProducerTemplate().sendBody("direct:start", "1,2,3");

        }
    }
}