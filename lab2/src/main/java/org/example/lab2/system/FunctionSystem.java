package org.example.lab2.system;

import org.example.lab2.core.AbstractTabularFunction;
import org.example.lab2.core.MathFunction;

import java.util.Map;

public class FunctionSystem extends AbstractTabularFunction {
    private final MathFunction f1;
    private final MathFunction f2;
    private final CalculationMode mode;

    public FunctionSystem(MathFunction f1, MathFunction f2, CalculationMode mode) {
        this.f1 = f1;
        this.f2 = f2;
        this.mode = mode;
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (Double.isNaN(x) || Double.isInfinite(x) || epsilon <= 0 || Double.isNaN(epsilon)) {
            return Double.NaN;
        }

        if (mode == CalculationMode.STUB) {
            return stub(x);
        }

        if (x <= 0.0) {
            return f1 == null ? Double.NaN : f1.calculate(x, epsilon);
        } else {
            return f2 == null ? Double.NaN : f2.calculate(x, epsilon);
        }
    }
}