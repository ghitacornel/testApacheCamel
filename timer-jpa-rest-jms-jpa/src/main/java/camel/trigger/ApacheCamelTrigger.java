package camel.trigger;

import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ApacheCamelTrigger {

    private final ProducerTemplate producerTemplate;

    @Scheduled(fixedRate = 3000)
    public void triggerApacheRoute() {
        producerTemplate.sendBody("direct:start", LocalDateTime.now());
    }

}
