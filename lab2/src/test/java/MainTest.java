import org.example.lab2.Main;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void mainRunsAndProducesCsv() throws Exception {
        Path file = Path.of("function-system.csv");
        try {
            Files.deleteIfExists(file);
            Main.main(new String[0]);
            assertTrue(Files.exists(file));
            assertTrue(Files.size(file) > 0);
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
