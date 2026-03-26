package log;

import org.example.lab2.log.Ln;
import org.example.lab2.log.Log5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Log5Test {

    private static final double EPS = 1e-6;
    private static final double DELTA = 1e-4;

    private final Ln ln = new Ln();
    private final Log5 log5 = new Log5(ln, EPS);

    @Test
    @DisplayName("log5(1) = 0")
    void shouldReturnZeroForOne() {
        double actual = log5.calculate(1.0, EPS);
        assertEquals(0.0, actual, DELTA);
    }

    @Test
    @DisplayName("log5(5) = 1")
    void shouldReturnOneForFive() {
        double actual = log5.calculate(5.0, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("log5(25) = 2")
    void shouldReturnTwoForTwentyFive() {
        double actual = log5.calculate(25.0, EPS);
        assertEquals(2.0, actual, DELTA);
    }

    @Test
    @DisplayName("log5(x) is negative for 0 < x < 1")
    void shouldReturnNegativeValueForFraction() {
        double actual = log5.calculate(0.5, EPS);
        assertTrue(actual < 0);
    }

    @Test
    @DisplayName("log5(x) is NaN for x = 0")
    void shouldReturnNaNForZero() {
        assertTrue(Double.isNaN(log5.calculate(0.0, EPS)));
    }

    @Test
    @DisplayName("log5(x) is NaN for negative x")
    void shouldReturnNaNForNegative() {
        assertTrue(Double.isNaN(log5.calculate(-5.0, EPS)));
    }

    @Test
    @DisplayName("log5(x) is NaN for NaN input")
    void shouldReturnNaNForNaNInput() {
        assertTrue(Double.isNaN(log5.calculate(Double.NaN, EPS)));
    }

    @Test
    @DisplayName("log5(x) is NaN for invalid epsilon")
    void shouldReturnNaNForInvalidEpsilon() {
        assertTrue(Double.isNaN(log5.calculate(5.0, 0.0)));
        assertTrue(Double.isNaN(log5.calculate(5.0, -1e-6)));
    }
}