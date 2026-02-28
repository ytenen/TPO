package lab1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SinSeriesTest {

  private static final double DELTA = 1e-8;
  private static final int DEFAULT_N = 100;

  //Специальные точки (кратные п/2)

  @Test
  @DisplayName("sin(0) = 0")
  void testSinZero() {
    assertEquals(0.0, SinSeries.sin(0.0, DEFAULT_N), DELTA);
  }

  @Test
  @DisplayName("sin(-0.0) = -0.0 (сохранение знака)")
  void testSinNegativeZero() {
    double result = SinSeries.sin(-0.0, DEFAULT_N);
    assertEquals(-0.0, result, DELTA);
    assertTrue(Double.doubleToRawLongBits(result) == Double.doubleToRawLongBits(-0.0));
  }

  @Test
  @DisplayName("sin(п/2) = 1.0 (максимум)")
  void testSinPiOver2() {
    assertEquals(1.0, SinSeries.sin(Math.PI / 2, DEFAULT_N), DELTA);
  }

  @Test
  @DisplayName("sin(-п/2) = -1.0 (минимум)")
  void testSinNegativePiOver2() {
    assertEquals(-1.0, SinSeries.sin(-Math.PI / 2, DEFAULT_N), DELTA);
  }

  @Test
  @DisplayName("sin(п) = 0")
  void testSinPi() {
    assertEquals(0.0, SinSeries.sin(Math.PI, DEFAULT_N), DELTA);
  }

  @Test
  @DisplayName("sin(-п) = 0")
  void testSinNegativePi() {
    assertEquals(0.0, SinSeries.sin(-Math.PI, DEFAULT_N), DELTA);
  }

  @Test
  @DisplayName("sin(2п) = 0 (период)")
  void testSin2Pi() {
    assertEquals(0.0, SinSeries.sin(2 * Math.PI, DEFAULT_N), DELTA);
  }

  @Test
  @DisplayName("sin(3п/2) = -1.0")
  void testSin3PiOver2() {
    assertEquals(-1.0, SinSeries.sin(3 * Math.PI / 2, DEFAULT_N), DELTA);
  }

  //Табличные значения (кратные п/6, п/4)

  @ParameterizedTest
  @DisplayName("Стандартные табличные значения")
  @CsvSource({
    "0.5235987756, 0.5",           // п/6
    "0.7853981634, 0.7071067812",  // п/4
    "1.0471975512, 0.8660254038",  // п/3
    "-0.5235987756, -0.5",         // -п/6
    "-0.7853981634, -0.7071067812",// -п/4
    "-1.0471975512, -0.8660254038" // -п/3
  })
  void testTableValues(double x, double expected) {
    assertEquals(expected, SinSeries.sin(x, DEFAULT_N), 1e-6);
  }

  //Особые случаи (NaN, Infinity)

  @Test
  @DisplayName("sin(NaN) = NaN")
  void testSinNaN() {
    assertTrue(Double.isNaN(SinSeries.sin(Double.NaN, DEFAULT_N)));
  }

  @Test
  @DisplayName("sin(+∞) = NaN")
  void testSinPositiveInfinity() {
    assertTrue(Double.isNaN(SinSeries.sin(Double.POSITIVE_INFINITY, DEFAULT_N)));
  }

  @Test
  @DisplayName("sin(-∞) = NaN")
  void testSinNegativeInfinity() {
    assertTrue(Double.isNaN(SinSeries.sin(Double.NEGATIVE_INFINITY, DEFAULT_N)));
  }

  //Малые значения около 0

  @ParameterizedTest
  @DisplayName("Малые значения: sin(x) ≈ x при x -> 0")
  @ValueSource(doubles = {0.001, 0.0001, 0.00001, 0.000001, -0.001, -0.0001, -0.00001, -0.000001})
  void testSmallValues(double x) {
    double expected = Math.sin(x);
    assertEquals(expected, SinSeries.sin(x, DEFAULT_N), 1e-12);
  }

  @Test
  @DisplayName("sin(Double.MIN_VALUE) != 0 (должен быть положительным)")
  void testSinMinValue() {
    double x = Double.MIN_VALUE;
    double result = SinSeries.sin(x, DEFAULT_N);
    assertTrue(result > 0, "Результат должен быть положительным");
    assertTrue(result != 0.0, "Результат не должен быть точно 0");
  }

  //Нормализация больших углов

  @ParameterizedTest
  @DisplayName("Периодичность: sin(x + 2пк) = sin(x) для разных к")
  @CsvSource({
    "0.5, 1",
    "1.0, 2",
    "-0.5, -1",
    "2.5, 5",
    "-1.5, -10",
    "0.1, 100"
  })
  void testPeriodicityWithDifferentK(double x, int k) {
    double sinX = SinSeries.sin(x, DEFAULT_N);
    double sinXPlusPeriod = SinSeries.sin(x + 2 * Math.PI * k, DEFAULT_N);
    assertEquals(sinX, sinXPlusPeriod, 1e-10,
      String.format("Нарушена периодичность для x=%.4f, к=%d", x, k));
  }

  @Test
  @DisplayName("Нормализация: sin(999) работает корректно")
  void testNormalizationLargeValue() {
    double x = 999.0;
    double normalized = x % (2 * Math.PI);
    double result = SinSeries.sin(x, DEFAULT_N);
    double expected = SinSeries.sin(normalized, DEFAULT_N);
    assertEquals(expected, result, 1e-6);
  }

  @Test
  @DisplayName("Нормализация: sin(-999) работает корректно")
  void testNormalizationLargeNegativeValue() {
    double x = -999.0;
    double normalized = x % (2 * Math.PI);
    double result = SinSeries.sin(x, DEFAULT_N);
    double expected = SinSeries.sin(normalized, DEFAULT_N);
    assertEquals(expected, result, 1e-6);
  }

  //Сетка значений

  @Test
  @DisplayName("Сетка: от -2π до 2π с шагом 0.1")
  void testGridValues() {
    double step = 0.1;
    double start = -2 * Math.PI;
    double end = 2 * Math.PI;

    for (double x = start; x <= end; x += step) {
      double expected = Math.sin(x);
      double actual = SinSeries.sin(x, DEFAULT_N);
      assertEquals(expected, actual, 1e-6, "Несовпадение при x = " + x);
    }
  }

  //Fuzzy тестирование

  @Test
  @DisplayName("Fuzzy: 10000 значений в одном периоде [0, 2п]")
  void testFuzzyOnePeriod() {
    Random random = new Random(42);
    for (int i = 0; i < 10000; i++) {
      double x = random.nextDouble() * 2 * Math.PI;
      assertEquals(Math.sin(x), SinSeries.sin(x, DEFAULT_N), 1e-6);
    }
  }

  @Test
  @DisplayName("Fuzzy: 10000 значений в широком диапазоне [-100, 100]")
  void testFuzzyLargeRange() {
    Random random = new Random(123);
    for (int i = 0; i < 10000; i++) {
      double x = random.nextDouble() * 200 - 100;
      assertEquals(Math.sin(x), SinSeries.sin(x, DEFAULT_N), 1e-6);
    }
  }

  //Свойства функции

  @ParameterizedTest
  @DisplayName("Нечетность: sin(-x) = -sin(x)")
  @ValueSource(doubles = {0.0, 0.5, 1.0, Math.PI / 4, Math.PI / 2, 10.0, 999.0})
  void testOddFunction(double x) {
    double sinX = SinSeries.sin(x, DEFAULT_N);
    double sinMinusX = SinSeries.sin(-x, DEFAULT_N);
    assertEquals(-sinX, sinMinusX, 1e-10);
  }

  @ParameterizedTest
  @DisplayName("Сдвиг на π: sin(x + п) = -sin(x)")
  @ValueSource(doubles = {0.0, 0.5, 1.0, Math.PI / 4, 2.0, 5.0})
  void testPiShift(double x) {
    double sinX = SinSeries.sin(x, DEFAULT_N);
    double sinXPlusPi = SinSeries.sin(x + Math.PI, DEFAULT_N);
    assertEquals(-sinX, sinXPlusPi, 1e-10);
  }

  //Сравнение с Math.sin

  @Test
  @DisplayName("Сравнение с Math.sin на различных значениях")
  void testComparisonWithMathSin() {
    double[] testValues = {
      -1000, -500, -100, -50, -10, -5, -2, -1,
      -0.5, -0.1, -0.01,
      0, 0.01, 0.1, 0.5,
      1, 2, 5, 10, 50, 100, 500, 1000,
      Math.PI / 6, Math.PI / 4, Math.PI / 3, Math.PI / 2,
      2 * Math.PI / 3, 3 * Math.PI / 4, 5 * Math.PI / 6
    };

    for (double x : testValues) {
      double expected = Math.sin(x);
      double actual = SinSeries.sin(x, DEFAULT_N);
      assertEquals(expected, actual, 1e-6,
        String.format("Несовпадение при x = %.4f", x));
    }
  }

  //Проверка точности при разном n

  @ParameterizedTest
  @DisplayName("Точность в зависимости от числа членов ряда")
  @CsvSource({
    "1.0, 5, 0.01",
    "1.0, 10, 1e-6",
    "1.0, 20, 1e-10",
    "3.0, 10, 0.1",
    "3.0, 20, 1e-6",
    "3.0, 30, 1e-10"
  })
  void testAccuracyWithDifferentN(double x, int n, double tolerance) {
    double expected = Math.sin(x);
    double actual = SinSeries.sin(x, n);
    assertEquals(expected, actual, tolerance);
  }

  @Test
  @DisplayName("sin(x) без параметра n использует значение по умолчанию")
  void testSinDefaultN() {
    double x = 1.0;
    double result = SinSeries.sin(x);
    double expected = Math.sin(x);
    assertEquals(expected, result, 1e-10);
  }

  @Test
  @DisplayName("Проверка ветки break: быстрая сходимость при малом x")
  void testBreakBranch() {
    double x = 1e-8;
    double result = SinSeries.sin(x, 1000);
    double expected = Math.sin(x);
    assertEquals(expected, result, 1e-10);
  }
}
