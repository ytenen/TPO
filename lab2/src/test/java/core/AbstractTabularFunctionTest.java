package core;

import org.example.lab2.core.AbstractTabularFunction;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTabularFunctionTest {

    private static final class DummyFunction extends AbstractTabularFunction {
        DummyFunction() {
            super();
        }

        DummyFunction(Map<Double, Double> table) {
            super(table);
        }

        @Override
        public double calculate(double x, double epsilon) {
            return x;
        }
    }

    @Test
    void stubReturnsNaNWhenNoValue() {
        DummyFunction f = new DummyFunction();
        assertTrue(Double.isNaN(f.stub(1.0)));
    }

    @Test
    void stubReturnsValueWhenPresent() {
        DummyFunction f = new DummyFunction();
        f.putStubValue(1.0, 2.0);
        assertEquals(2.0, f.stub(1.0));
    }

    @Test
    void constructorCopiesInitialTableAndTableIsUnmodifiable() {
        DummyFunction f = new DummyFunction(Map.of(1.0, 3.0));
        assertEquals(3.0, f.stub(1.0));
        assertThrows(UnsupportedOperationException.class, () -> f.getStubTable().put(2.0, 4.0));
    }
}
