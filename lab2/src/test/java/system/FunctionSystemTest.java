package system;

import org.example.lab2.system.CalculationMode;
import org.example.lab2.system.FunctionSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunctionSystemTest {

    private static final double EPS = 1e-6;

    private FunctionSystem system;

    @Mock
    private org.example.lab2.core.MathFunction f1;

    @Mock
    private org.example.lab2.core.MathFunction f2;

    @BeforeEach
    void setUp() {
        system = new FunctionSystem(f1, f2, CalculationMode.REAL);
    }

    @Test
    @DisplayName("System uses F1 branch for x < 0")
    void shouldUseF1ForNegativeX() {
        when(f1.calculate(eq(-1.0), eq(EPS))).thenReturn(123.0);
        double result = system.calculate(-1.0, EPS);
        assertTrue(Double.isFinite(result));
        verify(f1).calculate(eq(-1.0), eq(EPS));
        verifyNoInteractions(f2);
    }

    @Test
    @DisplayName("System returns NaN at x = 0")
    void shouldReturnNaNForZero() {
        when(f1.calculate(eq(0.0), eq(EPS))).thenReturn(Double.NaN);
        assertTrue(Double.isNaN(system.calculate(0.0, EPS)));
        verify(f1).calculate(eq(0.0), eq(EPS));
        verifyNoInteractions(f2);
    }

    @Test
    @DisplayName("System uses F2 branch for x > 0")
    void shouldUseF2ForPositiveX() {
        when(f2.calculate(eq(2.0), eq(EPS))).thenReturn(42.0);
        double result = system.calculate(2.0, EPS);
        assertTrue(Double.isFinite(result));
        verify(f2).calculate(eq(2.0), eq(EPS));
        verifyNoInteractions(f1);
    }

    @Test
    @DisplayName("System returns NaN at x = 1")
    void shouldReturnNaNForOne() {
        when(f2.calculate(eq(1.0), eq(EPS))).thenReturn(Double.NaN);
        assertTrue(Double.isNaN(system.calculate(1.0, EPS)));
        verify(f2).calculate(eq(1.0), eq(EPS));
        verifyNoInteractions(f1);
    }

    @Test
    @DisplayName("System handles value near 1 from left")
    void shouldReturnLargeNegativeNearOneFromLeft() {
        when(f2.calculate(eq(0.999), eq(EPS))).thenReturn(-1000.0);
        double result = system.calculate(0.999, EPS);
        assertTrue(Double.isFinite(result));
        assertTrue(result < 0);
        verify(f2).calculate(eq(0.999), eq(EPS));
    }

    @Test
    @DisplayName("System handles value near 1 from right")
    void shouldReturnLargePositiveNearOneFromRight() {
        when(f2.calculate(eq(1.001), eq(EPS))).thenReturn(1000.0);
        double result = system.calculate(1.001, EPS);
        assertTrue(Double.isFinite(result));
        assertTrue(result > 0);
        verify(f2).calculate(eq(1.001), eq(EPS));
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
