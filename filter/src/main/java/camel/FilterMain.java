package camel;

import camel.route.FilterRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class FilterMain {

    public static void main(String[] args) throws Exception {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            camelContext.addRoutes(new FilterRoute());
            camelContext.start();

            // nothing happens till we don't initiate a startup message
            // initiate a startup message
            // check 3rd message reached the end
            // check all the other messages are filtered out
            camelContext.createProducerTemplate().sendBody("direct:start", "dumpItOne");
            camelContext.createProducerTemplate().sendBody("direct:start", "dumpItTwo");
            camelContext.createProducerTemplate().sendBody("direct:start", "KeepIt");
            camelContext.createProducerTemplate().sendBody("direct:start", "dumpItThree");

        }
    }
}
