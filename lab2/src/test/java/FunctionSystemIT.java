import org.example.lab2.system.F1;
import org.example.lab2.system.F2;
import org.example.lab2.system.FunctionSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionSystemIT {
    private static final double EPS = Helper.EPS;

    @Test
    @DisplayName("Stubbed system should route to left and right branches")
    void shouldWorkAsFullyStubbedSystem() {
        FunctionSystem system = Helper.buildFullyStubbedSystem();

        double left = system.calculate(-1.0, EPS);
        double right = system.calculate(2.0, EPS);

        assertFalse(Double.isNaN(left));
        assertFalse(Double.isNaN(right));
    }

    @Test
    @DisplayName("Integration step: F1 with one real module")
    void shouldIntegrateF1StepByStep() {
        F1 f1 = Helper.buildF1WithRealSinOnly();

        double result = f1.calculate(-1.0, EPS);

        assertFalse(Double.isInfinite(result));
    }

    @Test
    @DisplayName("Integration step: F2 with one real module")
    void shouldIntegrateF2StepByStep() {
        F2 f2 = Helper.buildF2WithRealLnOnly();

        double result = f2.calculate(2.0, EPS);

        assertFalse(Double.isInfinite(result));
    }

    @Test
    @DisplayName("Integration step: fully real F1")
    void shouldIntegrateRealF1() {
        F1 f1 = Helper.buildRealF1();

        double result = f1.calculate(-1.0, EPS);

        assertTrue(Double.isFinite(result));
    }

    @Test
    @DisplayName("Integration step: fully real F2")
    void shouldIntegrateRealF2() {
        F2 f2 = Helper.buildRealF2();

        double result = f2.calculate(2.0, EPS);

        assertTrue(Double.isFinite(result));
    }

    @Test
    @DisplayName("Integration step: fully real function system")
    void shouldIntegrateWholeSystem() {
        FunctionSystem system = Helper.buildRealSystem();

        assertTrue(Double.isFinite(system.calculate(-1.0, EPS)));
        assertTrue(Double.isNaN(system.calculate(0.0, EPS)));
        assertTrue(Double.isNaN(system.calculate(1.0, EPS)));
        assertTrue(Double.isFinite(system.calculate(2.0, EPS)));
    }
}
