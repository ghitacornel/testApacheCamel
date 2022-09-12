package camel;

import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest
@CamelSpringBootTest
public class CsvJsonXmlRouteTest {

    @BeforeEach
    public void setUp() throws Exception {

        // delete working folder
        FileSystemUtils.deleteRecursively(Paths.get("target", "route"));
        Files.createDirectories(Paths.get("target", "route"));

        // copy input file
        {
            Path providedInputFile = Paths.get("src", "test", "resources", "camel", "input_data.csv");
            Path workingInputFile = Paths.get("target", "route", "input_data.csv");
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

        // verify json
        {
            Path expectedOutputFile = Paths.get("src", "test", "resources", "camel", "output_data.json");
            Path workingOutputFile = Paths.get("target", "route", "output_data.json");
            try {
                assertThat(Files.mismatch(expectedOutputFile, workingOutputFile)).isEqualTo(-1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
