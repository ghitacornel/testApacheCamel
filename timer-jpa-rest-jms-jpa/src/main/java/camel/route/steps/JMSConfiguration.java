package camel.route.steps;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@EnableJms
@Configuration
public class JMSConfiguration {

    @Bean
    Queue completedOrdersQueue() {
        return new ActiveMQQueue("CompletedOrdersQueueName");
    }

    @Bean
    Queue faileddOrdersQueue() {
        return new ActiveMQQueue("FailedOrdersQueueName");
    }

}
