package camel;

import org.apache.camel.CamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootMain.class, args);

        // nothing happens till we don't initiate a startup message
        // initiate a startup message
        // can be a dummy one
        CamelContext camelContext = context.getBean(CamelContext.class);
        camelContext.createProducerTemplate().sendBody("direct:start", "This is a dummy startup message");

    }
}
