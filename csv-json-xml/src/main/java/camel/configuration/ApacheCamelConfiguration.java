package camel.configuration;

import camel.route.Route;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApacheCamelConfiguration {

    @Bean
    CamelContext camelContext(ApplicationContext applicationContext, Route route) throws Exception {
        SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
        camelContext.disableJMX();
        camelContext.addRoutes(route);
        return camelContext;
    }

}