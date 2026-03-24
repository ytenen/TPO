package org.example.lab2.trig;

import org.example.lab2.core.AbstractTabularFunction;

import java.util.Map;

public class Cos extends AbstractTabularFunction {
    private final Sin sin;

    public Cos(Sin sin) {
        this.sin = sin;
    }

    public Cos(Sin sin, Map<Double, Double> stubTable) {
        super(stubTable);
        this.sin = sin;
    }

    @Override
    public double calculate(double x, double epsilon) {
        if (sin == null) {
            return Double.NaN;
        }
        return sin.calculate(x + Math.PI / 2.0, epsilon);
    }
}
