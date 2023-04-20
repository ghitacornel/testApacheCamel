package camel.trigger;

import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApacheCamelTrigger {

    private final ProducerTemplate producerTemplate;

    @Scheduled(fixedRate = 2000)
    public void triggerApacheRoute() {
        producerTemplate.sendBody("direct:start");
    }

}
