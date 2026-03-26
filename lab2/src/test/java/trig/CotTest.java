package trig;

import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Cot;
import org.example.lab2.trig.Sin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CotTest {

    private static final double EPS = 1e-6;
    private static final double DELTA = 1e-4;

    private final Sin sin = new Sin();
    private final Cos cos = new Cos(sin);
    private final Cot cot = new Cot(sin, cos);

    @Test
    @DisplayName("cot(pi/4) = 1")
    void shouldReturnOneForPiQuarter() {
        double actual = cot.calculate(Math.PI / 4.0, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("cot(-pi/4) = -1")
    void shouldReturnMinusOneForNegativePiQuarter() {
        double actual = cot.calculate(-Math.PI / 4.0, EPS);
        assertEquals(-1.0, actual, DELTA);
    }

    @Test
    @DisplayName("cot(x) is NaN at 0")
    void shouldReturnNaNAtZero() {
        double actual = cot.calculate(0.0, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("cot(x) is NaN at pi")
    void shouldReturnNaNAtPi() {
        double actual = cot.calculate(Math.PI, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("cot(x) is NaN at -pi")
    void shouldReturnNaNAtNegativePi() {
        double actual = cot.calculate(-Math.PI, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("cot(x) is odd: cot(-x) = -cot(x)")
    void shouldBeOddFunction() {
        double x = 0.6;
        double positive = cot.calculate(x, EPS);
        double negative = cot.calculate(-x, EPS);

        assertEquals(-positive, negative, DELTA);
    }

    @Test
    @DisplayName("cot(x) returns NaN if sin dependency is null")
    void shouldReturnNaNIfSinIsNull() {
        Cot brokenCot = new Cot(null, cos);
        assertTrue(Double.isNaN(brokenCot.calculate(1.0, EPS)));
    }

    @Test
    @DisplayName("cot(x) returns NaN if cos dependency is null")
    void shouldReturnNaNIfCosIsNull() {
        Cot brokenCot = new Cot(sin, null);
        assertTrue(Double.isNaN(brokenCot.calculate(1.0, EPS)));
    }
}
