package trig;


import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Sin;
import org.example.lab2.trig.Tan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TanTest {

    private static final double EPS = 1e-6;
    private static final double DELTA = 1e-4;

    private final Sin sin = new Sin();
    private final Cos cos = new Cos(sin);
    private final Tan tan = new Tan(sin, cos);

    @Test
    @DisplayName("tan(0) = 0")
    void shouldReturnZeroForZero() {
        double actual = tan.calculate(0.0, EPS);
        assertEquals(0.0, actual, DELTA);
    }

    @Test
    @DisplayName("tan(pi/4) = 1")
    void shouldReturnOneForPiQuarter() {
        double actual = tan.calculate(Math.PI / 4.0, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("tan(-pi/4) = -1")
    void shouldReturnMinusOneForNegativePiQuarter() {
        double actual = tan.calculate(-Math.PI / 4.0, EPS);
        assertEquals(-1.0, actual, DELTA);
    }

    @Test
    @DisplayName("tan(x) is NaN at pi/2")
    void shouldReturnNaNAtPiHalf() {
        double actual = tan.calculate(Math.PI / 2.0, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("tan(x) is NaN at -pi/2")
    void shouldReturnNaNAtNegativePiHalf() {
        double actual = tan.calculate(-Math.PI / 2.0, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("tan(x) is odd: tan(-x) = -tan(x)")
    void shouldBeOddFunction() {
        double x = 0.3;
        double positive = tan.calculate(x, EPS);
        double negative = tan.calculate(-x, EPS);

        assertEquals(-positive, negative, DELTA);
    }

    @Test
    @DisplayName("tan(x) returns NaN if sin dependency is null")
    void shouldReturnNaNIfSinIsNull() {
        Tan brokenTan = new Tan(null, cos);
        double result = brokenTan.calculate(1.0, EPS);
        assertTrue(Double.isNaN(result));
    }

    @Test
    @DisplayName("tan(x) returns NaN if cos dependency is null")
    void shouldReturnNaNIfCosIsNull() {
        Tan brokenTan = new Tan(sin, null);
        double result = brokenTan.calculate(1.0, EPS);
        assertTrue(Double.isNaN(result));
    }
}
