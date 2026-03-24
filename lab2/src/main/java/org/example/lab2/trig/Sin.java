package org.example.lab2.trig;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Sin extends AbstractTabularFunction {
    private static final int MAX_ITERATIONS = 1_000_000;

    public Sin() {
        super();
    }

    public Sin(Map<Double, Double> stubTable) {
        super(stubTable);
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (Double.isNaN(x) || Double.isInfinite(x) || epsilon <= 0 || Double.isNaN(epsilon)) {
            return Double.NaN;
        }

        double twoPi = 2.0 * Math.PI;
        double reduced = x % twoPi;
        if (reduced > Math.PI) {
            reduced -= twoPi;
        } else if (reduced < -Math.PI) {
            reduced += twoPi;
        }

        double term = reduced;
        double sum = term;

        for (int n = 1; n <= MAX_ITERATIONS; n++) {
            double denomA = (2.0 * n);
            double denomB = (2.0 * n + 1.0);
            term *= -reduced * reduced / (denomA * denomB);
            sum += term;
            if (Math.abs(term) < epsilon) {
                return sum;
            }
        }

        return Double.NaN;
    }
}
