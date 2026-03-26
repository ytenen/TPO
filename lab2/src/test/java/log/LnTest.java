package log;

import org.example.lab2.log.Ln;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LnTest {

    private static final double EPS = 1e-6;
    private static final double DELTA = 1e-4;

    private final Ln ln = new Ln();

    @Test
    @DisplayName("ln(1) = 0")
    void shouldReturnZeroForOne() {
        double actual = ln.calculate(1.0, EPS);
        assertEquals(0.0, actual, DELTA);
    }

    @Test
    @DisplayName("ln(2) = 0.693147")
    void shouldReturnCorrectValueForTwo() {
        double actual = ln.calculate(2.0, EPS);
        assertEquals(0.69314718056, actual, DELTA);
    }

    @Test
    @DisplayName("ln(0.5) = -0.693147")
    void shouldReturnCorrectValueForHalf() {
        double actual = ln.calculate(0.5, EPS);
        assertEquals(-0.69314718056, actual, DELTA);
    }

    @Test
    @DisplayName("ln(10) = 2.302585")
    void shouldReturnCorrectValueForTen() {
        double actual = ln.calculate(10.0, EPS);
        assertEquals(2.30258509299, actual, DELTA);
    }

    @Test
    @DisplayName("ln(e) = 1")
    void shouldReturnOneForEulerNumber() {
        double actual = ln.calculate(Math.E, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("ln(x) is NaN for x = 0")
    void shouldReturnNaNForZero() {
        double actual = ln.calculate(0.0, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("ln(x) is NaN for negative x")
    void shouldReturnNaNForNegativeValue() {
        double actual = ln.calculate(-1.0, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("ln(x) is NaN for NaN input")
    void shouldReturnNaNForNaNInput() {
        double actual = ln.calculate(Double.NaN, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("ln(x) is NaN for positive infinity")
    void shouldReturnNaNForPositiveInfinity() {
        double actual = ln.calculate(Double.POSITIVE_INFINITY, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("ln(x) is NaN for non-positive epsilon")
    void shouldReturnNaNForInvalidEpsilon() {
        assertTrue(Double.isNaN(ln.calculate(2.0, 0.0)));
        assertTrue(Double.isNaN(ln.calculate(2.0, -1e-6)));
    }

    @Test
    @DisplayName("ln(x) is increasing on positive values")
    void shouldBeMonotonicallyIncreasing() {
        double a = ln.calculate(0.5, EPS);
        double b = ln.calculate(1.0, EPS);
        double c = ln.calculate(2.0, EPS);

        assertTrue(a < b);
        assertTrue(b < c);
    }
}