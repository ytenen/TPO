package trig;

import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Cot;
import org.example.lab2.trig.Sin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CotTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin sin;

    @Mock
    private Cos cos;

    @Test
    @DisplayName("cot(x) = cos(x) / sin(x)")
    void shouldReturnCosDivSin() {
        Cot cot = new Cot(sin, cos);
        when(cos.calculate(eq(2.0), eq(EPS))).thenReturn(6.0);
        when(sin.calculate(eq(2.0), eq(EPS))).thenReturn(3.0);

        double actual = cot.calculate(2.0, EPS);

        assertEquals(2.0, actual);
        verify(cos).calculate(eq(2.0), eq(EPS));
        verify(sin).calculate(eq(2.0), eq(EPS));
        verifyNoMoreInteractions(sin, cos);
    }

    @Test
    @DisplayName("cot(x) returns NaN when sin(x) is too small")
    void shouldReturnNaNWhenSinIsTooSmall() {
        Cot cot = new Cot(sin, cos);
        when(cos.calculate(eq(2.0), eq(EPS))).thenReturn(1.0);
        when(sin.calculate(eq(2.0), eq(EPS))).thenReturn(EPS / 2.0);

        assertTrue(Double.isNaN(cot.calculate(2.0, EPS)));
    }

    @Test
    @DisplayName("cot(x) returns NaN if sin dependency is null")
    void shouldReturnNaNIfSinIsNull() {
        Cot brokenCot = new Cot(null, cos);
        assertTrue(Double.isNaN(brokenCot.calculate(1.0, EPS)));
    }

    @Test
    @DisplayName("cot(x) returns NaN if cos dependency is null")
    void shouldReturnNaNIfCosIsNull() {
        Cot brokenCot = new Cot(sin, null);
        assertTrue(Double.isNaN(brokenCot.calculate(1.0, EPS)));
    }
}
