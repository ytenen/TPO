package io;

import org.example.lab2.core.MathFunction;
import org.example.lab2.io.CsvExporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvExporterTest {

    private static final class LinearFunction implements MathFunction {
        @Override
        public double calculate(double x, double epsilon) {
            return 2.0 * x + 1.0;
        }

        @Override
        public double stub(double x) {
            return Double.NaN;
        }
    }

    @TempDir
    Path tempDir;

    @Test
    void exportWritesHeaderAndRows() throws IOException {
        Path file = tempDir.resolve("out.csv");
        CsvExporter exporter = new CsvExporter();

        exporter.export(new LinearFunction(), 0.0, 0.2, 0.1, 1e-6, file.toString());

        List<String> lines = Files.readAllLines(file);
        assertFalse(lines.isEmpty());
        assertEquals("x,result", lines.get(0));
        assertTrue(lines.size() >= 3);
    }

    @Test
    void exportRejectsInvalidArguments() {
        CsvExporter exporter = new CsvExporter();

        assertThrows(IllegalArgumentException.class, () -> exporter.export(null, 0, 1, 0.1, 1e-6, "a.csv"));
        assertThrows(IllegalArgumentException.class, () -> exporter.export(new LinearFunction(), Double.NaN, 1, 0.1, 1e-6, "a.csv"));
        assertThrows(IllegalArgumentException.class, () -> exporter.export(new LinearFunction(), 0, 1, -0.1, 1e-6, "a.csv"));
        assertThrows(IllegalArgumentException.class, () -> exporter.export(new LinearFunction(), 2, 1, 0.1, 1e-6, "a.csv"));
        assertThrows(IllegalArgumentException.class, () -> exporter.export(new LinearFunction(), 0, 1, 0.1, 0.0, "a.csv"));
        assertThrows(IllegalArgumentException.class, () -> exporter.export(new LinearFunction(), 0, 1, 0.1, 1e-6, ""));
    }
}
