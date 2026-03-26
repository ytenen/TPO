package system;

import org.example.lab2.log.Ln;
import org.example.lab2.log.Log10;
import org.example.lab2.log.Log3;
import org.example.lab2.log.Log5;
import org.example.lab2.system.CalculationMode;
import org.example.lab2.system.F2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class F2Test {

    private static final double EPS = 1e-6;

    private F2 f2;

    @BeforeEach
    void setUp() {
        Ln ln = new Ln();
        Log3 log3 = new Log3(ln, EPS);
        Log5 log5 = new Log5(ln, EPS);
        Log10 log10 = new Log10(ln, EPS);

        f2 = new F2(ln, log5, log10, log3, CalculationMode.REAL);
    }

    @Test
    @DisplayName("F2 returns finite value for x = 0.5")
    void shouldReturnFiniteValueForHalf() {
        double actual = f2.calculate(0.5, EPS);
        assertTrue(Double.isFinite(actual));
    }

    @Test
    @DisplayName("F2 returns finite value for x = 2")
    void shouldReturnFiniteValueForTwo() {
        double actual = f2.calculate(2.0, EPS);
        assertTrue(Double.isFinite(actual));
    }

    @Test
    @DisplayName("F2 returns finite value for x = 3")
    void shouldReturnFiniteValueForThree() {
        double actual = f2.calculate(3.0, EPS);
        assertTrue(Double.isFinite(actual));
    }

    @Test
    @DisplayName("F2 returns NaN for x = 1")
    void shouldReturnNaNForOne() {
        assertTrue(Double.isNaN(f2.calculate(1.0, EPS)));
    }

    @Test
    @DisplayName("F2 returns NaN for x = 0")
    void shouldReturnNaNForZero() {
        assertTrue(Double.isNaN(f2.calculate(0.0, EPS)));
    }

    @Test
    @DisplayName("F2 returns NaN for negative x")
    void shouldReturnNaNForNegativeX() {
        assertTrue(Double.isNaN(f2.calculate(-1.0, EPS)));
    }

    @Test
    @DisplayName("F2 returns large negative finite value near 1 from the left")
    void shouldReturnLargeNegativeValueNearOneFromLeft() {
        double actual = f2.calculate(0.999, EPS);
        assertTrue(Double.isFinite(actual));
        assertTrue(actual < 0.0);
    }

    @Test
    @DisplayName("F2 returns large positive finite value near 1 from the right")
    void shouldReturnLargePositiveValueNearOneFromRight() {
        double actual = f2.calculate(1.001, EPS);
        assertTrue(Double.isFinite(actual));
        assertTrue(actual > 0.0);
    }

    @Test
    @DisplayName("F2 returns NaN for NaN input")
    void shouldReturnNaNForNaNInput() {
        assertTrue(Double.isNaN(f2.calculate(Double.NaN, EPS)));
    }

    @Test
    @DisplayName("F2 returns NaN for positive infinity")
    void shouldReturnNaNForPositiveInfinity() {
        assertTrue(Double.isNaN(f2.calculate(Double.POSITIVE_INFINITY, EPS)));
    }

    @Test
    @DisplayName("F2 returns NaN for negative infinity")
    void shouldReturnNaNForNegativeInfinity() {
        assertTrue(Double.isNaN(f2.calculate(Double.NEGATIVE_INFINITY, EPS)));
    }

    @Test
    @DisplayName("F2 returns NaN for invalid epsilon")
    void shouldReturnNaNForInvalidEpsilon() {
        assertTrue(Double.isNaN(f2.calculate(2.0, 0.0)));
        assertTrue(Double.isNaN(f2.calculate(2.0, -1e-6)));
        assertTrue(Double.isNaN(f2.calculate(2.0, Double.NaN)));
    }
}
