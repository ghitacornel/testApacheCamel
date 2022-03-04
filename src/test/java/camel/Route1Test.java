package camel;

import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockEndpoints
public class Route1Test {

    @Test
    public void testRoute() throws Exception {

        // delete working folder
        FileSystemUtils.deleteRecursively(Paths.get("target", "route1"));
        Files.createDirectories(Paths.get("target", "route1"));

        // wait
        TimeUnit.SECONDS.sleep(1);

        // copy input file
        {
            Path providedInputFile = Paths.get("src", "test", "resources", "camel", "route1", "input_data.csv");
            Path workingInputFile = Paths.get("target", "route1", "input_data.csv");
            FileCopyUtils.copy(providedInputFile.toFile(), workingInputFile.toFile());
        }

        // wait for file processing
        TimeUnit.SECONDS.sleep(1);

        // verify
        {
            Path expectedOutputFile = Paths.get("src", "test", "resources", "camel", "route1", "reference_output_data.json");
            Path workingOutputFile = Paths.get("target", "route1", "output_data.json");
            Assertions.assertThat(Files.mismatch(expectedOutputFile, workingOutputFile)).isEqualTo(-1);
        }

    }
}
