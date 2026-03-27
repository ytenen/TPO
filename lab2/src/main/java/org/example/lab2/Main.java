package org.example.lab2;

import org.example.lab2.io.CsvConfig;
import org.example.lab2.io.CsvExporter;
import org.example.lab2.log.Ln;
import org.example.lab2.log.Log10;
import org.example.lab2.log.Log3;
import org.example.lab2.log.Log5;
import org.example.lab2.system.CalculationMode;
import org.example.lab2.system.F1;
import org.example.lab2.system.F2;
import org.example.lab2.system.FunctionSystem;
import org.example.lab2.trig.*;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        double epsilon = 1e-6;
        CsvExporter exporter = new CsvExporter();

        FunctionSystem system = buildFunctionSystem(epsilon);
        double[] xs = {-2.0, -1.0, -0.5, 0.5, 1.5, 2.0};

        for (double x : xs) {
            double y = system.calculate(x, epsilon);
            System.out.println("x = " + x + ", y = " + y);
        }

        exportAll(exporter, epsilon);
    }

    private static FunctionSystem buildFunctionSystem(double epsilon) {
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

        return new FunctionSystem(f1, f2, CalculationMode.REAL);
    }

    private static void exportAll(CsvExporter exporter, double epsilon) {
        try {
            Path outDir = Path.of("csv");
            Files.createDirectories(outDir);

            for (var task : CsvConfig.getTrigExportTasks(epsilon)) {
                export(exporter, task, epsilon, outDir);
            }

            for (var task : CsvConfig.getLogExportTasks(epsilon)) {
                export(exporter, task, epsilon, outDir);
            }

            for (var task : CsvConfig.getSystemExportTasks(epsilon)) {
                export(exporter, task, epsilon, outDir);
            }

            System.out.println("CSV exported successfully to " + outDir.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("CSV export failed: " + e.getMessage());
        }
    }

    private static void export(CsvExporter exporter,
                               CsvConfig.ExportTask task,
                               double epsilon,
                               Path outDir) throws Exception {
        exporter.export(
                task.getFunction(),
                task.getFrom(),
                task.getTo(),
                task.getStep(),
                epsilon,
                outDir.resolve(task.getFileName()).toString()
        );
    }
}