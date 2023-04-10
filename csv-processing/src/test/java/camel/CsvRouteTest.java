package camel;

import lombok.SneakyThrows;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@CamelSpringBootTest
public class CsvRouteTest {

    @Test
    @SneakyThrows
    public void testRoute() {

        // delete working folder
        Path root = Paths.get("target", "route");
        FileSystemUtils.deleteRecursively(root);
        Files.createDirectories(root);

        // copy input file
        {
            Path providedInputFile = Paths.get("src", "test", "resources", "io", "input_data.csv");
            Path workingInputFile = Paths.get("target", "route", "input_data.csv");
            FileCopyUtils.copy(providedInputFile.toFile(), workingInputFile.toFile());
        }
    }

}
