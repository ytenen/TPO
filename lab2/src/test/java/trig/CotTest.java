package trig;

import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Cot;
import org.example.lab2.trig.Sin;
import org.junit.jupiter.api.BeforeEach;
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
    private static final double DELTA = 1e-4;

    @Mock
    private Sin sin;

    @Mock
    private Cos cos;

    @BeforeEach
    void setUp() {
        sin = new Sin();
        cos = new Cos(sin);
    }

    @Test
    @DisplayName("cot(x) integration: real cos / mock sin")
    void shouldCalculateWithRealCosAndMockSin() {
        Cot cot = new Cot(sin, cos);

        double x = 2.0;
        double mockSinValue = 0.5;
        double expected = Math.cos(x) / mockSinValue;

        when(sin.calculate(eq(x), eq(EPS))).thenReturn(mockSinValue);

        double actual = cot.calculate(x, EPS);

        assertEquals(expected, actual, DELTA);
        verify(sin).calculate(eq(x), eq(EPS));
        verifyNoMoreInteractions(sin);
    }

    @Test
    @DisplayName("cot(x) integration: mock cos / real sin")
    void shouldCalculateWithMockCosAndRealSin() {
        Cot cot = new Cot(sin, cos);

        double x = 2.0;
        double mockCosValue = 0.8;
        double expected = mockCosValue / Math.sin(x);

        when(cos.calculate(eq(x), eq(EPS))).thenReturn(mockCosValue);

        double actual = cot.calculate(x, EPS);

        assertEquals(expected, actual, DELTA);
        verify(cos).calculate(eq(x), eq(EPS));
        verifyNoMoreInteractions(cos);
    }

    @Test
    @DisplayName("cot(x) integration: both mocked but with real-like behavior")
    void shouldCalculateWithBothMocked() {
        Cot cot = new Cot(sin, cos);

        double x = Math.PI / 3; // 60°
        double mockSinValue = Math.sin(x);
        double mockCosValue = Math.cos(x);
        double expected = mockCosValue / mockSinValue;

        when(sin.calculate(eq(x), eq(EPS))).thenReturn(mockSinValue);
        when(cos.calculate(eq(x), eq(EPS))).thenReturn(mockCosValue);

        double actual = cot.calculate(x, EPS);

        assertEquals(expected, actual, DELTA);
        verify(sin).calculate(eq(x), eq(EPS));
        verify(cos).calculate(eq(x), eq(EPS));
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
