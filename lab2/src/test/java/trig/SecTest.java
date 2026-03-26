package trig;

import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Sec;
import org.example.lab2.trig.Sin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecTest {

    private static final double EPS = 1e-6;
    private static final double DELTA = 1e-4;

    private final Sin sin = new Sin();
    private final Cos cos = new Cos(sin);
    private final Sec sec = new Sec(cos);

    @Test
    @DisplayName("sec(0) = 1")
    void shouldReturnOneForZero() {
        double actual = sec.calculate(0.0, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("sec(pi) = -1")
    void shouldReturnMinusOneForPi() {
        double actual = sec.calculate(Math.PI, EPS);
        assertEquals(-1.0, actual, DELTA);
    }

    @Test
    @DisplayName("sec(-pi) = -1")
    void shouldReturnMinusOneForNegativePi() {
        double actual = sec.calculate(-Math.PI, EPS);
        assertEquals(-1.0, actual, DELTA);
    }

    @Test
    @DisplayName("sec(x) is NaN at pi/2")
    void shouldReturnNaNAtPiHalf() {
        double actual = sec.calculate(Math.PI / 2.0, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("sec(x) is NaN at -pi/2")
    void shouldReturnNaNAtNegativePiHalf() {
        double actual = sec.calculate(-Math.PI / 2.0, EPS);
        assertTrue(Double.isNaN(actual));
    }

    @Test
    @DisplayName("sec(x) is even: sec(-x) = sec(x)")
    void shouldBeEvenFunction() {
        double x = 0.45;
        double positive = sec.calculate(x, EPS);
        double negative = sec.calculate(-x, EPS);

        assertEquals(positive, negative, DELTA);
    }

    @Test
    @DisplayName("sec(x) returns NaN if cos dependency is null")
    void shouldReturnNaNIfCosIsNull() {
        Sec brokenSec = new Sec(null);
        double result = brokenSec.calculate(1.0, EPS);
        assertTrue(Double.isNaN(result));
    }
}
