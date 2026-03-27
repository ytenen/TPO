package system;

import org.example.lab2.system.CalculationMode;
import org.example.lab2.system.F1;
import org.example.lab2.trig.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class F1Test {

    private static final double EPS = 1e-6;

    private F1 f1;

    @Mock
    private Sec sec;

    @Mock
    private Tan tan;

    @Mock
    private Csc csc;

    @Mock
    private Sin sin;

    @Mock
    private Cot cot;

    @BeforeEach
    void setUp() {
        f1 = new F1(sec, tan, csc, sin, cot, CalculationMode.REAL);
    }

    @Test
    @DisplayName("F1 returns finite value for regular negative x")
    void shouldReturnFiniteValueForRegularNegativePoint() {
        when(sec.calculate(eq(-1.0), eq(EPS))).thenReturn(2.0);
        when(tan.calculate(eq(-1.0), eq(EPS))).thenReturn(1.0);
        when(csc.calculate(eq(-1.0), eq(EPS))).thenReturn(1.0);
        when(sin.calculate(eq(-1.0), eq(EPS))).thenReturn(1.0);
        when(cot.calculate(eq(-1.0), eq(EPS))).thenReturn(1.0);

        double actual = f1.calculate(-1.0, EPS);
        assertTrue(Double.isFinite(actual));

        verify(sec).calculate(eq(-1.0), eq(EPS));
        verify(tan).calculate(eq(-1.0), eq(EPS));
        verify(csc).calculate(eq(-1.0), eq(EPS));
        verify(sin).calculate(eq(-1.0), eq(EPS));
        verify(cot).calculate(eq(-1.0), eq(EPS));
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
        when(sec.calculate(eq(-Math.PI), eq(EPS))).thenReturn(1.0);
        when(tan.calculate(eq(-Math.PI), eq(EPS))).thenReturn(1.0);
        when(csc.calculate(eq(-Math.PI), eq(EPS))).thenReturn(Double.NaN);
        when(sin.calculate(eq(-Math.PI), eq(EPS))).thenReturn(0.0);
        when(cot.calculate(eq(-Math.PI), eq(EPS))).thenReturn(Double.NaN);

        assertTrue(Double.isNaN(f1.calculate(-Math.PI, EPS)));
    }

    @Test
    @DisplayName("F1 returns NaN at x = -pi/2 because cos(x)=0")
    void shouldReturnNaNAtNegativePiHalf() {
        when(sec.calculate(eq(-Math.PI / 2.0), eq(EPS))).thenReturn(Double.NaN);
        when(tan.calculate(eq(-Math.PI / 2.0), eq(EPS))).thenReturn(Double.NaN);
        when(csc.calculate(eq(-Math.PI / 2.0), eq(EPS))).thenReturn(1.0);
        when(sin.calculate(eq(-Math.PI / 2.0), eq(EPS))).thenReturn(-1.0);
        when(cot.calculate(eq(-Math.PI / 2.0), eq(EPS))).thenReturn(0.0);

        assertTrue(Double.isNaN(f1.calculate(-Math.PI / 2.0, EPS)));
    }

    @Test
    @DisplayName("F1 returns NaN when denominator is zero (cot(x) + sec(x) = 0)")
    void shouldReturnNaNWhenDenominatorIsZero() {
        double x = -1.0;
        when(sec.calculate(eq(x), eq(EPS))).thenReturn(1.0);
        when(tan.calculate(eq(x), eq(EPS))).thenReturn(1.0);
        when(csc.calculate(eq(x), eq(EPS))).thenReturn(1.0);
        when(sin.calculate(eq(x), eq(EPS))).thenReturn(1.0);
        when(cot.calculate(eq(x), eq(EPS))).thenReturn(-1.0 + 5e-7);

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
