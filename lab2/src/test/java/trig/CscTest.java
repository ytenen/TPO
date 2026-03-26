package trig;

import org.example.lab2.trig.Csc;
import org.example.lab2.trig.Sin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CscTest {

    private static final double EPS = 1e-6;
    private static final double DELTA = 1e-4;

    private final Sin sin = new Sin();
    private final Csc csc = new Csc(sin);

    @Test
    @DisplayName("csc(pi/2) = 1")
    void shouldReturnOneForPiHalf() {
        double actual = csc.calculate(Math.PI / 2.0, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("csc(-pi/2) = -1")
    void shouldReturnMinusOneForNegativePiHalf() {
        double actual = csc.calculate(-Math.PI / 2.0, EPS);
        assertEquals(-1.0, actual, DELTA);
    }

    @Test
    @DisplayName("csc(x) is NaN at 0")
    void shouldReturnNaNAtZero() {
        double actual = csc.calculate(0.0, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("csc(x) is NaN at pi")
    void shouldReturnNaNAtPi() {
        double actual = csc.calculate(Math.PI, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("csc(x) is NaN at -pi")
    void shouldReturnNaNAtNegativePi() {
        double actual = csc.calculate(-Math.PI, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("csc(x) is odd: csc(-x) = -csc(x)")
    void shouldBeOddFunction() {
        double x = 0.5;
        double positive = csc.calculate(x, EPS);
        double negative = csc.calculate(-x, EPS);

        assertEquals(-positive, negative, DELTA);
    }

    @Test
    @DisplayName("csc(x) returns NaN if sin dependency is null")
    void shouldReturnNaNIfSinIsNull() {
        Csc brokenCsc = new Csc(null);
        double result = brokenCsc.calculate(1.0, EPS);
        assertTrue(Double.isNaN(result));
    }
}
