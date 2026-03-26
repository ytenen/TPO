package system;

import org.example.lab2.system.CalculationMode;
import org.example.lab2.system.F1;
import org.example.lab2.trig.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class F1Test {

    private static final double EPS = 1e-6;

    private F1 f1;

    @BeforeEach
    void setUp() {
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);

        f1 = new F1(sec, tan, csc, sin, cot, CalculationMode.REAL);
    }

    @Test
    @DisplayName("F1 returns finite value for regular negative x")
    void shouldReturnFiniteValueForRegularNegativePoint() {
        double actual = f1.calculate(-1.0, EPS);
        assertTrue(Double.isFinite(actual));
    }

    @Test
    @DisplayName("F1 returns NaN for x = 0")
    void shouldReturnNaNForZero() {
        assertTrue(Double.isNaN(f1.calculate(0.0, EPS)));
    }

    @Test
    @DisplayName("F1 returns NaN for positive x")
    void shouldReturnNaNForPositiveX() {
        assertTrue(Double.isNaN(f1.calculate(0.5, EPS)));
    }

    @Test
    @DisplayName("F1 returns NaN at x = -pi because sin(x)=0")
    void shouldReturnNaNAtNegativePi() {
        assertTrue(Double.isNaN(f1.calculate(-Math.PI, EPS)));
    }

    @Test
    @DisplayName("F1 returns NaN at x = -pi/2 because cos(x)=0")
    void shouldReturnNaNAtNegativePiHalf() {
        assertTrue(Double.isNaN(f1.calculate(-Math.PI / 2.0, EPS)));
    }

    @Test
    @DisplayName("F1 returns NaN when denominator is zero (cot(x) + sec(x) = 0)")
    void shouldReturnNaNWhenDenominatorIsZero() {
        double x = Math.asin((1 - Math.sqrt(5)) / 2);
        assertTrue(Double.isNaN(f1.calculate(x, EPS)));
    }

    @Test
    @DisplayName("F1 returns NaN for NaN input")
    void shouldReturnNaNForNaNInput() {
        assertTrue(Double.isNaN(f1.calculate(Double.NaN, EPS)));
    }

    @Test
    @DisplayName("F1 returns NaN for positive infinity")
    void shouldReturnNaNForPositiveInfinity() {
        assertTrue(Double.isNaN(f1.calculate(Double.POSITIVE_INFINITY, EPS)));
    }

    @Test
    @DisplayName("F1 returns NaN for invalid epsilon")
    void shouldReturnNaNForInvalidEpsilon() {
        assertTrue(Double.isNaN(f1.calculate(-1.0, 0.0)));
        assertTrue(Double.isNaN(f1.calculate(-1.0, -1e-6)));
        assertTrue(Double.isNaN(f1.calculate(-1.0, Double.NaN)));
    }
}
