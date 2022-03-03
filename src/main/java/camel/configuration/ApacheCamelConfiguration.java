package camel.configuration;

import camel.route1.Route1;
import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApacheCamelConfiguration {

    @Bean
    CamelContext camelContext(ApplicationContext applicationContext, Route1 route1) throws Exception {
        SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
        camelContext.disableJMX();
        camelContext.addRoutes(route1);
        return camelContext;
    }

}