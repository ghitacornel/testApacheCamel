package camel;

import camel.route.Snapshot;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MulticastParallelRouteTest {

    @Produce("direct:start")
    ProducerTemplate template;

    @Autowired
    Snapshot snapshot;

    @Test
    public void testRoute() throws InterruptedException {

        // create a dummy producer and send a dummy starting message
        template.sendBody("direct:start", "This is just a dummy startup message. It will be ignored");

        Thread.sleep(300);

        // check the snapshot and verify the parallel processing
        Assertions.assertThat(snapshot.getTrace().size()).isEqualTo(3);
        Assertions.assertThat(snapshot.getTrace().keySet()).contains("parallel step 1", "parallel step 2", "parallel step 3");
        Set<Thread> threads = new HashSet<>(snapshot.getTrace().values());
        Assertions.assertThat(threads.size()).isEqualTo(3);

    }
}
