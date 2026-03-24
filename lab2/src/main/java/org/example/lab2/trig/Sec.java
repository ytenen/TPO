package org.example.lab2.trig;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Sec extends AbstractTabularFunction {
    private final Cos cos;

    public Sec(Cos cos) {
        this.cos = cos;
    }

    public Sec(Cos cos, Map<Double, Double> stubTable) {
        super(stubTable);
        this.cos = cos;
    }

    @Override
    public double calculate(double x, double epsilon) {
        double cosX = cos.calculate(x, epsilon);
        if (Double.isNaN(cosX) || Math.abs(cosX) < epsilon) {
            return Double.NaN;
        }
        return 1.0 / cosX;
    }
}
