package trig;

import org.example.lab2.trig.Csc;
import org.example.lab2.trig.Sin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CscTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin sin;

    @Test
    @DisplayName("csc(x) = 1 / sin(x)")
    void shouldReturnReciprocalOfSin() {
        Csc csc = new Csc(sin);
        when(sin.calculate(eq(2.0), eq(EPS))).thenReturn(4.0);

        double actual = csc.calculate(2.0, EPS);

        assertEquals(0.25, actual);
        verify(sin).calculate(eq(2.0), eq(EPS));
        verifyNoMoreInteractions(sin);
    }

    @Test
    @DisplayName("csc(x) returns NaN when sin(x) is too small")
    void shouldReturnNaNWhenSinIsTooSmall() {
        Csc csc = new Csc(sin);
        when(sin.calculate(eq(2.0), eq(EPS))).thenReturn(EPS / 2.0);

        assertTrue(Double.isNaN(csc.calculate(2.0, EPS)));
    }

    @Test
    @DisplayName("csc(x) returns NaN when sin(x) is NaN")
    void shouldReturnNaNWhenSinIsNaN() {
        Csc csc = new Csc(sin);
        when(sin.calculate(eq(2.0), eq(EPS))).thenReturn(Double.NaN);

        assertTrue(Double.isNaN(csc.calculate(2.0, EPS)));
    }

    @Test
    @DisplayName("csc(x) returns NaN if sin dependency is null")
    void shouldReturnNaNIfSinIsNull() {
        Csc brokenCsc = new Csc(null);
        double result = brokenCsc.calculate(1.0, EPS);
        assertTrue(Double.isNaN(result));
    }
}
