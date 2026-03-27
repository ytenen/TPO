import org.example.lab2.system.F1;
import org.example.lab2.system.F2;
import org.example.lab2.system.FunctionSystem;
import org.example.lab2.system.CalculationMode;
import org.example.lab2.log.Ln;
import org.example.lab2.log.Log10;
import org.example.lab2.log.Log3;
import org.example.lab2.log.Log5;
import org.example.lab2.trig.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FunctionSystemIT {
    private static final double EPS = 1e-6;

    @Mock
    private F1 f1Mock;

    @Mock
    private F2 f2Mock;

    @Test
    @DisplayName("Stubbed system should route to left and right branches")
    void shouldWorkAsFullyStubbedSystem() {
        when(f1Mock.calculate(eq(-1.0), eq(EPS))).thenReturn(10.0);
        when(f2Mock.calculate(eq(2.0), eq(EPS))).thenReturn(20.0);
        FunctionSystem system = new FunctionSystem(f1Mock, f2Mock, CalculationMode.REAL);

        double left = system.calculate(-1.0, EPS);
        double right = system.calculate(2.0, EPS);

        assertFalse(Double.isNaN(left));
        assertFalse(Double.isNaN(right));

        verify(f1Mock).calculate(eq(-1.0), eq(EPS));
        verify(f2Mock).calculate(eq(2.0), eq(EPS));
    }

    @Test
    @DisplayName("Integration step: F1 with one real module")
    void shouldIntegrateF1StepByStep() {
        Sin realSin = new Sin();

        Sec sec = mock(Sec.class);
        Tan tan = mock(Tan.class);
        Csc csc = mock(Csc.class);
        Cot cot = mock(Cot.class);

        double x = -1.0;
        when(sec.calculate(eq(x), eq(EPS))).thenReturn(2.0);
        when(tan.calculate(eq(x), eq(EPS))).thenReturn(1.0);
        when(csc.calculate(eq(x), eq(EPS))).thenReturn(1.0);
        when(cot.calculate(eq(x), eq(EPS))).thenReturn(1.0);

        F1 f1 = new F1(sec, tan, csc, realSin, cot, CalculationMode.REAL);

        double result = f1.calculate(-1.0, EPS);

        assertFalse(Double.isInfinite(result));
    }

    @Test
    @DisplayName("Integration step: F2 with one real module")
    void shouldIntegrateF2StepByStep() {
        Ln realLn = new Ln();

        Log5 log5 = mock(Log5.class);
        Log10 log10 = mock(Log10.class);
        Log3 log3 = mock(Log3.class);

        double x = 2.0;
        when(log5.calculate(eq(x), eq(EPS))).thenReturn(2.2);
        when(log10.calculate(eq(x), eq(EPS))).thenReturn(3.3);
        when(log3.calculate(eq(x), eq(EPS))).thenReturn(4.4);

        F2 f2 = new F2(realLn, log5, log10, log3, CalculationMode.REAL);

        double result = f2.calculate(2.0, EPS);

        assertFalse(Double.isInfinite(result));
    }

    @Test
    @DisplayName("Integration step: fully real F1")
    void shouldIntegrateRealF1() {
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);
        F1 f1 = new F1(sec, tan, csc, sin, cot, CalculationMode.REAL);

        double result = f1.calculate(-1.0, EPS);

        assertTrue(Double.isFinite(result));
    }

    @Test
    @DisplayName("Integration step: fully real F2")
    void shouldIntegrateRealF2() {
        Ln ln = new Ln();
        Log3 log3 = new Log3(ln, EPS);
        Log5 log5 = new Log5(ln, EPS);
        Log10 log10 = new Log10(ln, EPS);
        F2 f2 = new F2(ln, log5, log10, log3, CalculationMode.REAL);

        double result = f2.calculate(2.0, EPS);

        assertTrue(Double.isFinite(result));
    }

    @Test
    @DisplayName("Integration step: fully real function system")
    void shouldIntegrateWholeSystem() {
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
        FunctionSystem system = new FunctionSystem(f1, f2, CalculationMode.REAL);

        assertTrue(Double.isFinite(system.calculate(-1.0, EPS)));
        assertTrue(Double.isNaN(system.calculate(0.0, EPS)));
        assertTrue(Double.isNaN(system.calculate(1.0, EPS)));
        assertTrue(Double.isFinite(system.calculate(2.0, EPS)));
    }
}
