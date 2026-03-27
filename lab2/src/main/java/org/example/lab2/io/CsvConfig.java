package org.example.lab2.io;

import org.example.lab2.core.MathFunction;
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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvConfig {

    public static class ExportTask {
        private final MathFunction function;
        private final double from;
        private final double to;
        private final double step;
        private final String fileName;

        public ExportTask(MathFunction function, double from, double to, double step, String fileName) {
            this.function = function;
            this.from = from;
            this.to = to;
            this.step = step;
            this.fileName = fileName;
        }

        public MathFunction getFunction() { return function; }
        public double getFrom() { return from; }
        public double getTo() { return to; }
        public double getStep() { return step; }
        public String getFileName() { return fileName; }
    }

    public static List<ExportTask> getTrigExportTasks(double epsilon) {
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);

        return List.of(
                new ExportTask(sin, -5.0, 5.0, 0.1, "sin.csv"),
                new ExportTask(cos, -5.0, 5.0, 0.1, "cos.csv"),
                new ExportTask(tan, -5.0, 5.0, 0.1, "tan.csv"),
                new ExportTask(cot, -5.0, 5.0, 0.1, "cot.csv"),
                new ExportTask(sec, -5.0, 5.0, 0.1, "sec.csv"),
                new ExportTask(csc, -5.0, 5.0, 0.1, "csc.csv")
        );
    }

    public static List<ExportTask> getLogExportTasks(double epsilon) {
        Ln ln = new Ln();
        Log3 log3 = new Log3(ln, epsilon);
        Log5 log5 = new Log5(ln, epsilon);
        Log10 log10 = new Log10(ln, epsilon);

        return List.of(
                new ExportTask(ln, 0.1, 5.0, 0.1, "ln.csv"),
                new ExportTask(log3, 0.1, 5.0, 0.1, "log3.csv"),
                new ExportTask(log5, 0.1, 5.0, 0.1, "log5.csv"),
                new ExportTask(log10, 0.1, 5.0, 0.1, "log10.csv")
        );
    }

    public static List<ExportTask> getSystemExportTasks(double epsilon) {
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

        return List.of(
                new ExportTask(f1, -5.0, 0.0, 0.1, "f1.csv"),
                new ExportTask(f2, 0.1, 5.0, 0.1, "f2.csv"),
                new ExportTask(system, -5.0, 5.0, 0.1, "function-system.csv")
        );
    }
}