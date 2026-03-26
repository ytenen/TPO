package org.example.lab2.trig;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Tan extends AbstractTabularFunction {
    private final Sin sin;
    private final Cos cos;

    public Tan(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public Tan(Sin sin, Cos cos, Map<Double, Double> stubTable) {
        super(stubTable);
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (sin == null || cos == null || Double.isNaN(epsilon) || epsilon <= 0.0) {
            return Double.NaN;
        }

        double sinX = sin.calculate(x, epsilon);
        double cosX = cos.calculate(x, epsilon);

        if (Double.isNaN(sinX) || Double.isNaN(cosX) || Math.abs(cosX) < epsilon) {
            return Double.NaN;
        }

        return sinX / cosX;
    }
}
