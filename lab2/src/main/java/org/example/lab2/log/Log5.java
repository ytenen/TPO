package org.example.lab2.log;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Log5 extends AbstractTabularFunction {
    private final Ln ln;
    private final double ln5;

    public Log5(Ln ln, double epsilonForConstant) {
        this.ln = ln;
        this.ln5 = ln.calculate(5.0, epsilonForConstant);
    }

    public Log5(Ln ln, double epsilonForConstant, Map<Double, Double> stubTable) {
        super(stubTable);
        this.ln = ln;
        this.ln5 = ln.calculate(5.0, epsilonForConstant);
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (x <= 0.0 || Double.isNaN(x) || Double.isInfinite(x)) {
            return Double.NaN;
        }
        double lnX = ln.calculate(x, epsilon);
        if (Double.isNaN(lnX) || Double.isNaN(ln5) || ln5 == 0.0) {
            return Double.NaN;
        }
        return lnX / ln5;
    }
}
