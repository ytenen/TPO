package org.example.lab2.system;

import org.example.lab2.core.AbstractTabularFunction;
import org.example.lab2.core.MathFunction;

import java.util.Map;

public class F1 extends AbstractTabularFunction {
    private final MathFunction sec;
    private final MathFunction tan;
    private final MathFunction csc;
    private final MathFunction sin;
    private final MathFunction cot;
    private final CalculationMode mode;

    public F1(MathFunction sec, MathFunction tan, MathFunction csc, MathFunction sin, MathFunction cot, CalculationMode mode) {
        this.sec = sec;
        this.tan = tan;
        this.csc = csc;
        this.sin = sin;
        this.cot = cot;
        this.mode = mode;
    }

    public F1(MathFunction sec, MathFunction tan, MathFunction csc, MathFunction sin, MathFunction cot,
              CalculationMode mode, Map<Double, Double> stubTable) {
        super(stubTable);
        this.sec = sec;
        this.tan = tan;
        this.csc = csc;
        this.sin = sin;
        this.cot = cot;
        this.mode = mode;
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (Double.isNaN(x) || Double.isInfinite(x) || epsilon <= 0.0 || Double.isNaN(epsilon)) {
            return Double.NaN;
        }

        if (x > 0.0) {
            return Double.NaN;
        }

        double secX = value(sec, x, epsilon);
        double tanX = value(tan, x, epsilon);
        double cscX = value(csc, x, epsilon);
        double sinX = value(sin, x, epsilon);
        double cotX = value(cot, x, epsilon);

        if (Double.isNaN(secX) || Double.isNaN(tanX) || Double.isNaN(cscX)
                || Double.isNaN(sinX) || Double.isNaN(cotX)) {
            return Double.NaN;
        }

        if (Math.abs(secX) < epsilon) {
            return Double.NaN;
        }

        double secSquared = secX * secX;
        double tanDivSec = tanX / secX;
        double numerator = (secSquared + tanDivSec) - cscX - sinX;
        double denominator = cotX + secX;

        if (Math.abs(denominator) < epsilon) {
            return Double.NaN;
        }

        return numerator / denominator;
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