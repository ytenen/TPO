package trig;

import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Sin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CosTest {

    private static final double EPS = 1e-6;

    @Mock
    private Sin sin;

    @Test
    @DisplayName("cos(x) delegates to sin(x + pi/2)")
    void shouldDelegateToSinWithShift() {
        Cos cos = new Cos(sin);

        when(sin.calculate(eq(0.0 + Math.PI / 2.0), eq(EPS))).thenReturn(1.0);

        double actual = cos.calculate(0.0, EPS);

        assertEquals(1.0, actual);
        verify(sin).calculate(eq(0.0 + Math.PI / 2.0), eq(EPS));
        verifyNoMoreInteractions(sin);
    }

    @Test
    @DisplayName("cos(x) returns NaN if sin dependency is null")
    void shouldReturnNaNIfSinIsNull() {
        Cos brokenCos = new Cos(null);
        double result = brokenCos.calculate(1.0, EPS);
        assertTrue(Double.isNaN(result));
    }
}
