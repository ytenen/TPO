package log;

import org.example.lab2.log.Ln;
import org.example.lab2.log.Log3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Log3Test {

    private static final double EPS = 1e-6;
    private static final double DELTA = 1e-4;

    private final Ln ln = new Ln();
    private final Log3 log3 = new Log3(ln, EPS);

    @Test
    @DisplayName("log3(1) = 0")
    void shouldReturnZeroForOne() {
        double actual = log3.calculate(1.0, EPS);
        assertEquals(0.0, actual, DELTA);
    }

    @Test
    @DisplayName("log3(3) = 1")
    void shouldReturnOneForThree() {
        double actual = log3.calculate(3.0, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("log3(9) = 2")
    void shouldReturnTwoForNine() {
        double actual = log3.calculate(9.0, EPS);
        assertEquals(2.0, actual, DELTA);
    }

    @Test
    @DisplayName("log3(x) is negative for 0 < x < 1")
    void shouldReturnNegativeValueForFraction() {
        double actual = log3.calculate(0.5, EPS);
        assertTrue(actual < 0);
    }

    @Test
    @DisplayName("log3(x) is NaN for x = 0")
    void shouldReturnNaNForZero() {
        assertTrue(Double.isNaN(log3.calculate(0.0, EPS)));
    }

    @Test
    @DisplayName("log3(x) is NaN for negative x")
    void shouldReturnNaNForNegative() {
        assertTrue(Double.isNaN(log3.calculate(-3.0, EPS)));
    }

    @Test
    @DisplayName("log3(x) is NaN for NaN input")
    void shouldReturnNaNForNaNInput() {
        assertTrue(Double.isNaN(log3.calculate(Double.NaN, EPS)));
    }

    @Test
    @DisplayName("log3(x) is NaN for invalid epsilon")
    void shouldReturnNaNForInvalidEpsilon() {
        assertTrue(Double.isNaN(log3.calculate(3.0, 0.0)));
        assertTrue(Double.isNaN(log3.calculate(3.0, -1e-6)));
    }
}
