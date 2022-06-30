package camel;

import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@CamelSpringBootTest
public class ParallelRouteTest {

    @Test
    public void testRoute() throws Exception {

        // delete working folder
        FileSystemUtils.deleteRecursively(Paths.get("target", "route"));
        Files.createDirectories(Paths.get("target", "route"));

        // copy input file
        {
            Path providedInputFile = Paths.get("src", "test", "resources", "camel", "input.txt");
            Path workingInputFile = Paths.get("target", "route", "input.txt");
            FileCopyUtils.copy(providedInputFile.toFile(), workingInputFile.toFile());
        }

        Thread.sleep(100);

        // verify file
        {
            Path expectedOutputFile = Paths.get("src", "test", "resources", "camel", "output.txt");
            Path workingOutputFile = Paths.get("target", "route", "output.txt");
            try {
                assertThat(Files.mismatch(expectedOutputFile, workingOutputFile)).isEqualTo(-1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
