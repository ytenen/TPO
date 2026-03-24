package org.example.lab2.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTabularFunction implements MathFunction {
    private final Map<Double, Double> table = new HashMap<>();

    protected AbstractTabularFunction() {
    }

    protected AbstractTabularFunction(Map<Double, Double> initialTable) {
        if (initialTable != null) {
            table.putAll(initialTable);
        }
    }

    public void putStubValue(double x, double value) {
        table.put(x, value);
    }

    public Map<Double, Double> getStubTable() {
        return Collections.unmodifiableMap(table);
    }

    @Override
    public double stub(double x) {
        Double value = table.get(x);
        return value == null ? Double.NaN : value;
    }
}
