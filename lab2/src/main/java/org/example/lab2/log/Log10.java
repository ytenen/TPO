package org.example.lab2.log;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Log10 extends AbstractTabularFunction {
    private final Ln ln;
    private final double ln10;

    public Log10(Ln ln, double epsilonForConstant) {
        this.ln = ln;
        this.ln10 = ln.calculate(10.0, epsilonForConstant);
    }

    public Log10(Ln ln, double epsilonForConstant, Map<Double, Double> stubTable) {
        super(stubTable);
        this.ln = ln;
        this.ln10 = ln.calculate(10.0, epsilonForConstant);
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (x <= 0.0 || Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        }
        double lnX = ln.calculate(x, epsilon);
        if (Double.isNaN(lnX) || Double.isNaN(ln10) || ln10 == 0.0) {
            return Double.NaN;
        }
        return lnX / ln10;
    }
}
