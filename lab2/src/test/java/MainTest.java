import org.example.lab2.Main;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void mainRunsAndProducesCsv() throws Exception {
        Path dir = Path.of("csv");
        Path[] files = new Path[]{
                dir.resolve("sin.csv"),
                dir.resolve("cos.csv"),
                dir.resolve("tan.csv"),
                dir.resolve("cot.csv"),
                dir.resolve("sec.csv"),
                dir.resolve("csc.csv"),
                dir.resolve("ln.csv"),
                dir.resolve("log3.csv"),
                dir.resolve("log5.csv"),
                dir.resolve("log10.csv"),
                dir.resolve("f1.csv"),
                dir.resolve("f2.csv"),
                dir.resolve("function-system.csv")
        };
        try {
            if (Files.exists(dir)) {
                for (Path file : files) {
                    Files.deleteIfExists(file);
                }
            }
            for (Path file : files) {
                Files.deleteIfExists(file);
            }
            Main.main(new String[0]);
            for (Path file : files) {
                assertTrue(Files.exists(file));
                assertTrue(Files.size(file) > 0);
            }
        } finally {
            for (Path file : files) {
                Files.deleteIfExists(file);
            }
            if (Files.exists(dir)) {
                Files.deleteIfExists(dir);
            }
        }
    }
}
