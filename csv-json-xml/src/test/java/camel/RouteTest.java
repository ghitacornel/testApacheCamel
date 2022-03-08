package camel;

import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;

import javax.xml.transform.Source;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@CamelSpringBootTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouteTest {

    @Test
    public void testRoute() throws Exception {

        // delete working folder
        FileSystemUtils.deleteRecursively(Paths.get("target", "route"));
        Files.createDirectories(Paths.get("target", "route"));

        // wait
        TimeUnit.SECONDS.sleep(1);

        // copy input file
        {
            Path providedInputFile = Paths.get("src", "test", "resources", "camel", "input_data.csv");
            Path workingInputFile = Paths.get("target", "route", "input_data.csv");
            FileCopyUtils.copy(providedInputFile.toFile(), workingInputFile.toFile());
        }

        // wait for file processing
        TimeUnit.SECONDS.sleep(1);

        // verify json
        {
            Path expectedOutputFile = Paths.get("src", "test", "resources", "camel", "output_data.json");
            Path workingOutputFile = Paths.get("target", "route", "output_data.json");
            assertThat(Files.mismatch(expectedOutputFile, workingOutputFile)).isEqualTo(-1);
        }

        // verify xml
        {
            Path expectedOutputFile = Paths.get("src", "test", "resources", "camel", "output_data.xml");
            Path workingOutputFile = Paths.get("target", "route", "output_data.xml");

            Source control = Input.fromPath(expectedOutputFile).build();
            Source test = Input.fromPath(workingOutputFile).build();
            Assertions.assertThat(
                    DiffBuilder.compare(test).withTest(control)
                            .ignoreWhitespace()
                            .ignoreComments()
                            .checkForSimilar()
                            .build()
                            .hasDifferences()
            ).isFalse();

        }

    }
}