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
        if (Double.isNaN(x) || x <= 0.0 || Double.isInfinite(x) || epsilon <= 0 || Double.isNaN(epsilon)) {
            return Double.NaN;
        }

        int k = 0;
        double m = x;
        while (m >= 2.0) {
            m /= 2.0;
            k++;
            if (k > 10_000) {
                return Double.NaN;
            }
        }
        while (m < 1.0) {
            m *= 2.0;
            k--;
            if (k < -10_000) {
                return Double.NaN;
            }
        }

        double ln2 = ln1pSeries(1.0, epsilon);
        double lnm = ln1pSeries(m - 1.0, epsilon);
        if (Double.isNaN(ln2) || Double.isNaN(lnm)) {
            return Double.NaN;
        }

        return lnm + k * ln2;
    }

    private double ln1pSeries(double y, double epsilon) {
        if (Double.isNaN(y) || y <= -1.0) {
            return Double.NaN;
        }

        if (Math.abs(y) >= 1.0) {
            return Double.NaN;
        }

        double term = y;
        double sum = term;

        for (int n = 2; n <= MAX_ITERATIONS; n++) {
            term *= -y * (n - 1.0) / n;
            sum += term;
            if (Math.abs(term) < epsilon) {
                return sum;
            }
        }

        return Double.NaN;
    }
}
