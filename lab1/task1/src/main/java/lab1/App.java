package lab1;

import java.util.Scanner;

public class App {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 50;
        
        System.out.println("===Разложение sin(x) в степенной ряд===");
        System.out.println("Количество членов ряда по умолчанию: " + n);
        
        while (true) {
            System.out.print("\nВведите x (или 'exit' для выхода): ");
            
            if (!scanner.hasNextDouble()) {
                String input = scanner.next();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println("Некорректный ввод!");
                continue;
            }
            
            double x = scanner.nextDouble();
            double result = SinSeries.sin(x, n);
            double expected = Math.sin(x);
            double error = Math.abs(result - expected);
            
            System.out.printf("x = %.6f%n", x);
            System.out.printf("Наш результат:     %.10f%n", result);
            System.out.printf("Math.sin(x):       %.10f%n", expected);
            System.out.printf("Абсолютная ошибка: %.2e%n", error);
        }
    }
}
