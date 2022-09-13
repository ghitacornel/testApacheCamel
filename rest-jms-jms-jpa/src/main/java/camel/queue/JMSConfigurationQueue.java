package camel.queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@EnableJms
@Configuration
public class JMSConfigurationQueue {

    public static final String QUEUE_1 = "FirstQueue";
    public static final String QUEUE_2 = "SecondQueue";

    @Bean
    Queue queue1() {
        return new ActiveMQQueue(QUEUE_1);
    }

    @Bean
    Queue queue2() {
        return new ActiveMQQueue(QUEUE_2);
    }

}
