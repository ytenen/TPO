package trig;

import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Sec;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecTest {

    private static final double EPS = 1e-6;

    @Mock
    private Cos cos;

    @Test
    @DisplayName("sec(x) = 1 / cos(x)")
    void shouldReturnReciprocalOfCos() {
        Sec sec = new Sec(cos);
        when(cos.calculate(eq(2.0), eq(EPS))).thenReturn(4.0);

        double actual = sec.calculate(2.0, EPS);

        assertEquals(0.25, actual);
        verify(cos).calculate(eq(2.0), eq(EPS));
        verifyNoMoreInteractions(cos);
    }

    @Test
    @DisplayName("sec(x) returns NaN when cos(x) is too small")
    void shouldReturnNaNWhenCosIsTooSmall() {
        Sec sec = new Sec(cos);
        when(cos.calculate(eq(2.0), eq(EPS))).thenReturn(EPS / 2.0);

        assertTrue(Double.isNaN(sec.calculate(2.0, EPS)));
    }

    @Test
    @DisplayName("sec(x) returns NaN when cos(x) is NaN")
    void shouldReturnNaNWhenCosIsNaN() {
        Sec sec = new Sec(cos);
        when(cos.calculate(eq(2.0), eq(EPS))).thenReturn(Double.NaN);

        assertTrue(Double.isNaN(sec.calculate(2.0, EPS)));
    }

    @Test
    @DisplayName("sec(x) returns NaN if cos dependency is null")
    void shouldReturnNaNIfCosIsNull() {
        Sec brokenSec = new Sec(null);
        double result = brokenSec.calculate(1.0, EPS);
        assertTrue(Double.isNaN(result));
    }
}
