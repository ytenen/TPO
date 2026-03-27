package org.example.lab2.log;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Log3 extends AbstractTabularFunction {
    private final Ln ln;
    private final double ln3;

    public Log3(Ln ln, double epsilonForConstant) {
        this.ln = ln;
        this.ln3 = ln == null ? Double.NaN : ln.calculate(3.0, epsilonForConstant);
    }

    public Log3(Ln ln, double epsilonForConstant, Map<Double, Double> stubTable) {
        super(stubTable);
        this.ln = ln;
        this.ln3 = ln == null ? Double.NaN : ln.calculate(3.0, epsilonForConstant);
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (ln == null || x <= 0.0 || Double.isNaN(x) || Double.isInfinite(x)
                || epsilon <= 0.0 || Double.isNaN(epsilon)) {
            return Double.NaN;
        }

        double lnX = ln.calculate(x, epsilon);
        if (Double.isNaN(lnX) || Double.isNaN(ln3) || Math.abs(ln3) < epsilon) {
            return Double.NaN;
        }

        return lnX / ln3;
    }
}
