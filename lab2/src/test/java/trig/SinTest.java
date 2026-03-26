package trig;

import org.example.lab2.trig.Sin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinTest {

    private static final double EPS = 1e-6;
    private static final double DELTA = 1e-4;

    private final Sin sin = new Sin();

    @Test
    @DisplayName("sin(0) = 0")
    void shouldReturnZeroForZero() {
        double actual = sin.calculate(0.0, EPS);
        assertEquals(0.0, actual, DELTA);
    }

    @Test
    @DisplayName("sin(pi/2) = 1")
    void shouldReturnOneForPiHalf() {
        double actual = sin.calculate(Math.PI / 2.0, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("sin(-pi/2) = -1")
    void shouldReturnMinusOneForNegativePiHalf() {
        double actual = sin.calculate(-Math.PI / 2.0, EPS);
        assertEquals(-1.0, actual, DELTA);
    }

    @Test
    @DisplayName("sin(pi) = 0")
    void shouldReturnZeroForPi() {
        double actual = sin.calculate(Math.PI, EPS);
        assertEquals(0.0, actual, DELTA);
    }

    @Test
    @DisplayName("sin(-pi) = 0")
    void shouldReturnZeroForNegativePi() {
        double actual = sin.calculate(-Math.PI, EPS);
        assertEquals(0.0, actual, DELTA);
    }

    @Test
    @DisplayName("sin(2pi) = 0")
    void shouldReturnZeroForTwoPi() {
        double actual = sin.calculate(2.0 * Math.PI, EPS);
        assertEquals(0.0, actual, DELTA);
    }

    @Test
    @DisplayName("sin(x) is odd: sin(-x) = -sin(x)")
    void shouldBeOddFunction() {
        double x = 0.7;
        double positive = sin.calculate(x, EPS);
        double negative = sin.calculate(-x, EPS);

        assertEquals(-positive, negative, DELTA);
    }

    @Test
    @DisplayName("sin(x) returns NaN for NaN input")
    void shouldReturnNaNForNaNInput() {
        double actual = sin.calculate(Double.NaN, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("sin(x) returns NaN for infinity")
    void shouldReturnNaNForInfinityInput() {
        assertTrue(Double.isNaN(sin.calculate(Double.POSITIVE_INFINITY, EPS)));
        assertTrue(Double.isNaN(sin.calculate(Double.NEGATIVE_INFINITY, EPS)));
    }

    @Test
    @DisplayName("sin(x) returns NaN for non-positive epsilon")
    void shouldReturnNaNForInvalidEpsilon() {
        assertTrue(Double.isNaN(sin.calculate(1.0, 0.0)));
        assertTrue(Double.isNaN(sin.calculate(1.0, -1e-6)));
        assertTrue(Double.isNaN(sin.calculate(1.0, Double.NaN)));
    }
}
