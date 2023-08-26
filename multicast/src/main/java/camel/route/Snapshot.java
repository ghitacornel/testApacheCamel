package camel.route;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Snapshot {

    private final Map<String, Thread> trace = new ConcurrentHashMap<>();

}
