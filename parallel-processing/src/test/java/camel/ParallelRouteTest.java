package camel;

import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@CamelSpringBootTest
@SpringBootTest
public class ParallelRouteTest {

    @BeforeEach
    public void setUp() throws Exception {

        // delete working folder
        FileSystemUtils.deleteRecursively(Paths.get("target", "route"));
        Files.createDirectories(Paths.get("target", "route"));

        // copy input file
        {
            Path providedInputFile = Paths.get("src", "test", "resources", "camel", "input.txt");
            Path workingInputFile = Paths.get("target", "route", "input.txt");
            FileCopyUtils.copy(providedInputFile.toFile(), workingInputFile.toFile());
        }

    }

    @Test
    public void testRoute() {

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
