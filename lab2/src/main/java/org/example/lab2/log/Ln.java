package org.example.lab2.log;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Ln extends AbstractTabularFunction {
    private static final int MAX_ITERATIONS = 1_000_000;

    public Ln() {
        super();
    }

    public Ln(Map<Double, Double> stubTable) {
        super(stubTable);
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (Double.isNaN(x) || Double.isInfinite(x) || x <= 0.0
                || Double.isNaN(epsilon) || epsilon <= 0.0) {
            return Double.NaN;
        }

        double z = (x - 1.0) / (x + 1.0);
        double zPow = z;
        double sum = 0.0;

        for (int n = 1; n <= MAX_ITERATIONS; n += 2) {
            double term = zPow / n;
            sum += term;

            if (Math.abs(term) < epsilon) {
                return 2.0 * sum;
            }

            zPow *= z * z;
        }

        return Double.NaN;
    }
}