package trig;

import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Sin;
import org.example.lab2.trig.Tan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TanTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin sin;

    @Mock
    private Cos cos;

    @Test
    @DisplayName("tan(x) = sin(x) / cos(x)")
    void shouldReturnSinDivCos() {
        Tan tan = new Tan(sin, cos);
        when(sin.calculate(eq(1.0), eq(EPS))).thenReturn(2.0);
        when(cos.calculate(eq(1.0), eq(EPS))).thenReturn(4.0);

        double actual = tan.calculate(1.0, EPS);

        assertEquals(0.5, actual);
        verify(sin).calculate(eq(1.0), eq(EPS));
        verify(cos).calculate(eq(1.0), eq(EPS));
        verifyNoMoreInteractions(sin, cos);
    }

    @Test
    @DisplayName("tan(x) returns NaN when cos(x) is too small")
    void shouldReturnNaNWhenCosIsTooSmall() {
        Tan tan = new Tan(sin, cos);
        when(sin.calculate(eq(1.0), eq(EPS))).thenReturn(1.0);
        when(cos.calculate(eq(1.0), eq(EPS))).thenReturn(EPS / 2.0);

        assertTrue(Double.isNaN(tan.calculate(1.0, EPS)));

        verify(sin).calculate(eq(1.0), eq(EPS));
        verify(cos).calculate(eq(1.0), eq(EPS));
    }

    @Test
    @DisplayName("tan(x) returns NaN if sin returns NaN")
    void shouldReturnNaNIfSinIsNaN() {
        Tan tan = new Tan(sin, cos);
        when(sin.calculate(eq(1.0), eq(EPS))).thenReturn(Double.NaN);
        when(cos.calculate(eq(1.0), eq(EPS))).thenReturn(1.0);

        assertTrue(Double.isNaN(tan.calculate(1.0, EPS)));
    }

    @Test
    @DisplayName("tan(x) returns NaN if sin dependency is null")
    void shouldReturnNaNIfSinIsNull() {
        Tan brokenTan = new Tan(null, cos);
        assertTrue(Double.isNaN(brokenTan.calculate(1.0, EPS)));
    }

    @Test
    @DisplayName("tan(x) returns NaN if cos dependency is null")
    void shouldReturnNaNIfCosIsNull() {
        Tan brokenTan = new Tan(sin, null);
        assertTrue(Double.isNaN(brokenTan.calculate(1.0, EPS)));
    }
}
