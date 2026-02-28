package lab1;

public class SinSeries {

  public static double sin(double x) {
    return sin(x, 1000);
  }

  public static double sin(double x, int n) {
    if (Double.isNaN(x)) {
      return Double.NaN;
    }

    if (x == -0d) {
      return -0d;
    }

    x = normalizeAngle(x);

    double res = x;
    double term = x;

    for (int i = 1; i < n; i++) {
      double old = res;

      // Вычисляем новый член ряда
      term *= -x * x / ((2 * i) * (2 * i + 1));
      res += term;

      // Проверка сходимости: если изменение меньше epsilon или член ряда очень мал
      if (Math.abs(term) < 1e-15 || Math.abs(res - old) < 1e-15) break;
    }

    return res;
  }

  //Нормализуем угол к диапазону [-2п, 2п] используя модуль
  private static double normalizeAngle(double x) {
    double twoPi = 2 * Math.PI;
    return x % twoPi;
  }
}
