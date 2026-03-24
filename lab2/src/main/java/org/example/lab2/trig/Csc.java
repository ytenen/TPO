package org.example.lab2.trig;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Csc extends AbstractTabularFunction {
    private final Sin sin;

    public Csc(Sin sin) {
        this.sin = sin;
    }

    public Csc(Sin sin, Map<Double, Double> stubTable) {
        super(stubTable);
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double epsilon) {
        double sinX = sin.calculate(x, epsilon);
        if (Double.isNaN(sinX) || Math.abs(sinX) < epsilon) {
            return Double.NaN;
        }
        return 1.0 / sinX;
    }
}
