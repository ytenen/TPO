package trig;


import org.example.lab2.trig.Cos;
import org.example.lab2.trig.Sin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CosTest {

    private static final double EPS = 1e-6;
    private static final double DELTA = 1e-4;

    private final Sin sin = new Sin();
    private final Cos cos = new Cos(sin);

    @Test
    @DisplayName("cos(0) = 1")
    void shouldReturnOneForZero() {
        double actual = cos.calculate(0.0, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("cos(pi/2) = 0")
    void shouldReturnZeroForPiHalf() {
        double actual = cos.calculate(Math.PI / 2.0, EPS);
        assertEquals(0.0, actual, DELTA);
    }

    @Test
    @DisplayName("cos(pi) = -1")
    void shouldReturnMinusOneForPi() {
        double actual = cos.calculate(Math.PI, EPS);
        assertEquals(-1.0, actual, DELTA);
    }

    @Test
    @DisplayName("cos(-pi) = -1")
    void shouldReturnMinusOneForNegativePi() {
        double actual = cos.calculate(-Math.PI, EPS);
        assertEquals(-1.0, actual, DELTA);
    }

    @Test
    @DisplayName("cos(2pi) = 1")
    void shouldReturnOneForTwoPi() {
        double actual = cos.calculate(2.0 * Math.PI, EPS);
        assertEquals(1.0, actual, DELTA);
    }

    @Test
    @DisplayName("cos(x) is even: cos(-x) = cos(x)")
    void shouldBeEvenFunction() {
        double x = 0.73;

        double positive = cos.calculate(x, EPS);
        double negative = cos.calculate(-x, EPS);

        assertEquals(positive, negative, DELTA);
    }

    @Test
    @DisplayName("cos(x) returns NaN if sin dependency is null")
    void shouldReturnNaNIfSinIsNull() {
        Cos brokenCos = new Cos(null);
        double result = brokenCos.calculate(1.0, EPS);
        assertTrue(Double.isNaN(result));
    }
}
