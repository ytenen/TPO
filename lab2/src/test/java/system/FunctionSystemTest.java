package system;

import org.example.lab2.log.Ln;
import org.example.lab2.log.Log10;
import org.example.lab2.log.Log3;
import org.example.lab2.log.Log5;
import org.example.lab2.system.CalculationMode;
import org.example.lab2.system.F1;
import org.example.lab2.system.F2;
import org.example.lab2.system.FunctionSystem;
import org.example.lab2.trig.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionSystemTest {

    private static final double EPS = 1e-6;

    private FunctionSystem system;

    @BeforeEach
    void setUp() {

        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);

        Ln ln = new Ln();
        Log3 log3 = new Log3(ln, EPS);
        Log5 log5 = new Log5(ln, EPS);
        Log10 log10 = new Log10(ln, EPS);

        F1 f1 = new F1(sec, tan, csc, sin, cot, CalculationMode.REAL);
        F2 f2 = new F2(ln, log5, log10, log3, CalculationMode.REAL);

        system = new FunctionSystem(f1, f2, CalculationMode.REAL);
    }

    @Test
    @DisplayName("System uses F1 branch for x < 0")
    void shouldUseF1ForNegativeX() {
        double result = system.calculate(-1.0, EPS);
        assertTrue(Double.isFinite(result));
    }

    @Test
    @DisplayName("System returns NaN at x = 0")
    void shouldReturnNaNForZero() {
        assertTrue(Double.isNaN(system.calculate(0.0, EPS)));
    }

    @Test
    @DisplayName("System uses F2 branch for x > 0")
    void shouldUseF2ForPositiveX() {
        double result = system.calculate(2.0, EPS);
        assertTrue(Double.isFinite(result));
    }

    @Test
    @DisplayName("System returns NaN at x = 1")
    void shouldReturnNaNForOne() {
        assertTrue(Double.isNaN(system.calculate(1.0, EPS)));
    }

    @Test
    @DisplayName("System handles value near 1 from left")
    void shouldReturnLargeNegativeNearOneFromLeft() {
        double result = system.calculate(0.999, EPS);
        assertTrue(Double.isFinite(result));
        assertTrue(result < 0);
    }

    @Test
    @DisplayName("System handles value near 1 from right")
    void shouldReturnLargePositiveNearOneFromRight() {
        double result = system.calculate(1.001, EPS);
        assertTrue(Double.isFinite(result));
        assertTrue(result > 0);
    }

    @Test
    @DisplayName("System returns NaN for NaN input")
    void shouldReturnNaNForNaNInput() {
        assertTrue(Double.isNaN(system.calculate(Double.NaN, EPS)));
    }

    @Test
    @DisplayName("System returns NaN for positive infinity")
    void shouldReturnNaNForPositiveInfinity() {
        assertTrue(Double.isNaN(system.calculate(Double.POSITIVE_INFINITY, EPS)));
    }

    @Test
    @DisplayName("System returns NaN for negative infinity")
    void shouldReturnNaNForNegativeInfinity() {
        assertTrue(Double.isNaN(system.calculate(Double.NEGATIVE_INFINITY, EPS)));
    }

    @Test
    @DisplayName("System returns NaN for invalid epsilon")
    void shouldReturnNaNForInvalidEpsilon() {
        assertTrue(Double.isNaN(system.calculate(2.0, 0.0)));
        assertTrue(Double.isNaN(system.calculate(2.0, -1e-6)));
        assertTrue(Double.isNaN(system.calculate(2.0, Double.NaN)));
    }
}