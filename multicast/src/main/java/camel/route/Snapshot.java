package camel.route;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class Snapshot {

    private final Map<String, Thread> trace = new ConcurrentHashMap<>();

}
