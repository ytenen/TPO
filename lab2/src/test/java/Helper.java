import org.example.lab2.log.Ln;
import org.example.lab2.log.Log10;
import org.example.lab2.log.Log3;
import org.example.lab2.log.Log5;
import org.example.lab2.system.CalculationMode;
import org.example.lab2.system.F1;
import org.example.lab2.system.F2;
import org.example.lab2.system.FunctionSystem;
import org.example.lab2.trig.*;

import java.util.Map;

public class Helper {
    public static final double EPS = 1e-6;

    private Helper() {
    }

    public static FunctionSystem buildRealSystem() {
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

        return new FunctionSystem(f1, f2, CalculationMode.REAL);
    }

    public static FunctionSystem buildFullyStubbedSystem() {
        Sin sinStub = new Sin(Map.of(
                -1.0, -10.0
        ));

        Tan tanStub = new Tan(null, null, Map.of(
                -1.0, -20.0
        ));

        Csc cscStub = new Csc(null, Map.of(
                -1.0, -30.0
        ));

        Sec secStub = new Sec(null, Map.of(
                -1.0, 40.0
        ));

        Cot cotStub = new Cot(null, null, Map.of(
                -1.0, 50.0
        ));

        Ln lnStub = new Ln(Map.of(
                2.0, 1.1
        ));

        Log5 log5Stub = new Log5(null, EPS, Map.of(
                2.0, 2.2
        ));

        Log10 log10Stub = new Log10(null, EPS, Map.of(
                2.0, 3.3
        ));

        Log3 log3Stub = new Log3(null, EPS, Map.of(
                2.0, 4.4
        ));

        F1 f1Stub = new F1(secStub, tanStub, cscStub, sinStub, cotStub, CalculationMode.STUB);
        F2 f2Stub = new F2(lnStub, log5Stub, log10Stub, log3Stub, CalculationMode.STUB);

        return new FunctionSystem(f1Stub, f2Stub, CalculationMode.REAL);
    }

    public static F1 buildF1WithRealSinOnly() {
        Sin sin = new Sin();

        Sec secStub = new Sec(null, Map.of(-1.0, 40.0));
        Tan tanStub = new Tan(null, null, Map.of(-1.0, -20.0));
        Csc cscStub = new Csc(null, Map.of(-1.0, -30.0));
        Cot cotStub = new Cot(null, null, Map.of(-1.0, 50.0));

        return new F1(secStub, tanStub, cscStub, sin, cotStub, CalculationMode.REAL);
    }

    public static F2 buildF2WithRealLnOnly() {
        Ln ln = new Ln();

        Log5 log5Stub = new Log5(null, EPS, Map.of(2.0, 2.2));
        Log10 log10Stub = new Log10(null, EPS, Map.of(2.0, 3.3));
        Log3 log3Stub = new Log3(null, EPS, Map.of(2.0, 4.4));

        return new F2(ln, log5Stub, log10Stub, log3Stub, CalculationMode.REAL);
    }

    public static F1 buildRealF1() {
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);

        return new F1(sec, tan, csc, sin, cot, CalculationMode.REAL);
    }

    public static F2 buildRealF2() {
        Ln ln = new Ln();
        Log3 log3 = new Log3(ln, EPS);
        Log5 log5 = new Log5(ln, EPS);
        Log10 log10 = new Log10(ln, EPS);

        return new F2(ln, log5, log10, log3, CalculationMode.REAL);
    }
}
