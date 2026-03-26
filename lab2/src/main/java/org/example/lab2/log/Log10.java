package org.example.lab2.log;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Log10 extends AbstractTabularFunction {
    private final Ln ln;
    private final double ln10;

    public Log10(Ln ln, double epsilonForConstant) {
        this.ln = ln;
        this.ln10 = ln == null ? Double.NaN : ln.calculate(10.0, epsilonForConstant);
    }

    public Log10(Ln ln, double epsilonForConstant, Map<Double, Double> stubTable) {
        super(stubTable);
        this.ln = ln;
        this.ln10 = ln == null ? Double.NaN : ln.calculate(10.0, epsilonForConstant);
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (ln == null || x <= 0.0 || Double.isNaN(x) || Double.isInfinite(x)
                || epsilon <= 0.0 || Double.isNaN(epsilon)) {
            return Double.NaN;
        }

        double lnX = ln.calculate(x, epsilon);
        if (Double.isNaN(lnX) || Double.isNaN(ln10) || Math.abs(ln10) < epsilon) {
            return Double.NaN;
        }

        return lnX / ln10;
    }
}