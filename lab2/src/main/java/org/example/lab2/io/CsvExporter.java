package org.example.lab2.io;

import org.example.lab2.core.MathFunction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class CsvExporter {

    public void export(MathFunction function,
                       double from,
                       double to,
                       double step,
                       double epsilon,
                       String filePath) throws IOException {

        if (function == null || Double.isNaN(from) || Double.isNaN(to)
                || Double.isNaN(step) || Double.isNaN(epsilon)
                || step <= 0.0 || epsilon <= 0.0 || filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Invalid export arguments");
        }

        if (from > to) {
            throw new IllegalArgumentException("from must be <= to");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("x,result");
            writer.newLine();

            for (double x = from; x <= to + step / 2.0; x += step) {
                double y = function.calculate(x, epsilon);

                String xStr = format(x);
                String yStr = Double.isNaN(y) ? "NaN" : format(y);

                writer.write(xStr + "," + yStr);
                writer.newLine();
            }
        }
    }

    private String format(double value) {
        return String.format(Locale.US, "%.10f", value);
    }
}