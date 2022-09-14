package camel.route;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class Snapshot {

    private final Map<String, Thread> trace = new HashMap<>();

}
