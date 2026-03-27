package org.example.lab2;

import org.example.lab2.io.CsvExporter;
import org.example.lab2.log.Ln;
import org.example.lab2.log.Log10;
import org.example.lab2.log.Log3;
import org.example.lab2.log.Log5;
import org.example.lab2.system.CalculationMode;
import org.example.lab2.system.F1;
import org.example.lab2.system.F2;
import org.example.lab2.system.FunctionSystem;
import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Cot;
import org.example.lab2.trig.Csc;
import org.example.lab2.trig.Sec;
import org.example.lab2.trig.Sin;
import org.example.lab2.trig.Tan;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        double epsilon = 1e-6;

        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);

        Ln ln = new Ln();
        Log3 log3 = new Log3(ln, epsilon);
        Log5 log5 = new Log5(ln, epsilon);
        Log10 log10 = new Log10(ln, epsilon);

        F1 f1 = new F1(sec, tan, csc, sin, cot, CalculationMode.REAL);
        F2 f2 = new F2(ln, log5, log10, log3, CalculationMode.REAL);

        FunctionSystem system = new FunctionSystem(f1, f2, CalculationMode.REAL);

        double[] xs = {-2.0, -1.0, -0.5, 0.5, 1.5, 2.0};

        for (double x : xs) {
            double y = system.calculate(x, epsilon);
            System.out.println("x = " + x + ", y = " + y);
        }

        CsvExporter exporter = new CsvExporter();
        try {
            Path outDir = Path.of("csv");
            Files.createDirectories(outDir);

            export(exporter, sin, -5.0, 5.0, 0.1, epsilon, outDir.resolve("sin.csv").toString());
            export(exporter, cos, -5.0, 5.0, 0.1, epsilon, outDir.resolve("cos.csv").toString());
            export(exporter, tan, -5.0, 5.0, 0.1, epsilon, outDir.resolve("tan.csv").toString());
            export(exporter, cot, -5.0, 5.0, 0.1, epsilon, outDir.resolve("cot.csv").toString());
            export(exporter, sec, -5.0, 5.0, 0.1, epsilon, outDir.resolve("sec.csv").toString());
            export(exporter, csc, -5.0, 5.0, 0.1, epsilon, outDir.resolve("csc.csv").toString());

            export(exporter, ln, 0.1, 5.0, 0.1, epsilon, outDir.resolve("ln.csv").toString());
            export(exporter, log3, 0.1, 5.0, 0.1, epsilon, outDir.resolve("log3.csv").toString());
            export(exporter, log5, 0.1, 5.0, 0.1, epsilon, outDir.resolve("log5.csv").toString());
            export(exporter, log10, 0.1, 5.0, 0.1, epsilon, outDir.resolve("log10.csv").toString());

            export(exporter, f1, -5.0, 0.0, 0.1, epsilon, outDir.resolve("f1.csv").toString());
            export(exporter, f2, 0.1, 5.0, 0.1, epsilon, outDir.resolve("f2.csv").toString());

            export(exporter, system, -5.0, 5.0, 0.1, epsilon, outDir.resolve("function-system.csv").toString());
            System.out.println("CSV exported");
        } catch (Exception e) {
            System.out.println("CSV export failed: " + e.getMessage());
        }
    }

    private static void export(CsvExporter exporter,
                               org.example.lab2.core.MathFunction function,
                               double from,
                               double to,
                               double step,
                               double epsilon,
                               String filePath) throws Exception {
        exporter.export(function, from, to, step, epsilon, filePath);
    }
}
