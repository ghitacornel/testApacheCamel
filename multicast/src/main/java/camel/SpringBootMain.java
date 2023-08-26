package camel;

import org.apache.camel.CamelContext;

public class SpringBootMain {

    public static void main(String[] args) {
        CamelContext camelContext = null;

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

    }
}
