package org.example.lab2.system;

import org.example.lab2.core.AbstractTabularFunction;
import org.example.lab2.core.MathFunction;

import java.util.Map;

public class F2 extends AbstractTabularFunction {
    private final MathFunction ln;
    private final MathFunction log5;
    private final MathFunction log10;
    private final MathFunction log3;
    private final CalculationMode mode;

    public F2(MathFunction ln, MathFunction log5, MathFunction log10, MathFunction log3, CalculationMode mode) {
        this.ln = ln;
        this.log5 = log5;
        this.log10 = log10;
        this.log3 = log3;
        this.mode = mode;
    }

    public F2(MathFunction ln, MathFunction log5, MathFunction log10, MathFunction log3, CalculationMode mode,
              Map<Double, Double> stubTable) {
        super(stubTable);
        this.ln = ln;
        this.log5 = log5;
        this.log10 = log10;
        this.log3 = log3;
        this.mode = mode;
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (Double.isNaN(x) || Double.isInfinite(x) || epsilon <= 0 || Double.isNaN(epsilon)) {
            return Double.NaN;
        }



        if (x <= 0.0) {
            return Double.NaN;
        }

        double lnX = value(ln, x, epsilon);
        double log5X = value(log5, x, epsilon);
        double log10X = value(log10, x, epsilon);
        double log3X = value(log3, x, epsilon);

        if (Double.isNaN(lnX) || Double.isNaN(log5X) || Double.isNaN(log10X) || Double.isNaN(log3X)) {
            return Double.NaN;
        }

        if (Math.abs(log10X) < epsilon || Math.abs(log3X) < epsilon) {
            return Double.NaN;
        }

        double part1 = (lnX + log5X) / log10X;
        double part2 = part1 / log10X;
        double left = part2 + log3X;
        double right = log10X / log3X;

        return left - right;
    }

    private double value(MathFunction function, double x, double epsilon) {
        if (function == null) {
            return Double.NaN;
        }
        if (mode == CalculationMode.STUB) {
            return function.stub(x);
        }
        return function.calculate(x, epsilon);
    }
}