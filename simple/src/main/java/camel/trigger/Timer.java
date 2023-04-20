package camel.trigger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Timer {

    @Scheduled(fixedRate = 2000)
    public void reportCurrentTime() {
        System.out.println(this + " stupid timer was triggered at " + new Date());
    }

}
