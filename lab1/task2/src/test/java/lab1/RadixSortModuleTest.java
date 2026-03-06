package lab1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Модульное тестирование алгоритма поразрядной сортировки (Radix Sort).
 * Тесты проверяют, что выполнение алгоритма следует ожидаемой
 * последовательности характерных точек для различных наборов входных данных.
 *
 * Характерные точки - это ключевые места в алгоритме, которые отслеживаются
 * во время выполнения для проверки правильности потока управления.
 */
public class RadixSortModuleTest {

    private RadixSort radixSort;

    @BeforeEach
    void setUp() {
        radixSort = new RadixSort();
        radixSort.setTracingEnabled(true);
    }

    // ============== Тестирование базовых характерных точек ==============

    @Test
    @DisplayName("Проверка характерных точек для пустого массива")
    void testEmptyArrayCharacteristicPoints() {
        int[] arr = {};

        List<RadixSort.CharacteristicPoint> referenceSequence = Arrays.asList(
            RadixSort.CharacteristicPoint.START,
            RadixSort.CharacteristicPoint.FINISH
        );

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> actual = radixSort.getExecutionTrace();

        assertEquals(referenceSequence, actual,
            "Для пустого массива должны быть только точки START и FINISH");
    }

    @Test
    @DisplayName("Проверка характерных точек для одного элемента [5]")
    void testSingleElementCharacteristicPoints() {
        int[] arr = {5};

        // Эталонная последовательность для одного элемента (max=5, 1 разряд)
        List<RadixSort.CharacteristicPoint> referenceSequence = Arrays.asList(
            RadixSort.CharacteristicPoint.START,
            // Поиск максимума
            RadixSort.CharacteristicPoint.FIND_MAX,
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP,
            // Первый разряд (единицы)
            RadixSort.CharacteristicPoint.NEXT_DIGIT,
            RadixSort.CharacteristicPoint.COUNTING_SORT,
            RadixSort.CharacteristicPoint.COUNT_ARRAY_INIT,
            RadixSort.CharacteristicPoint.COUNT_DIGITS,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP,
            RadixSort.CharacteristicPoint.COMPUTE_PREFIX_SUM,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP,
            RadixSort.CharacteristicPoint.COPY_TO_ORIGINAL,
            RadixSort.CharacteristicPoint.COPY_LOOP,
            // Конец (max/10=0, следующий разряд не нужен)
            RadixSort.CharacteristicPoint.FINISH
        );

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> actual = radixSort.getExecutionTrace();

        assertEquals(referenceSequence, actual,
            "Последовательность характерных точек для [5] должна совпадать с эталонной");
        assertEquals(5, arr[0], "Массив должен остаться неизменным");
    }

    @Test
    @DisplayName("Проверка характерных точек для двух элементов [3, 7]")
    void testTwoElementsCharacteristicPoints() {
        int[] arr = {3, 7};

        // Эталонная последовательность для двух элементов (max=7, 1 разряд)
        List<RadixSort.CharacteristicPoint> referenceSequence = Arrays.asList(
            RadixSort.CharacteristicPoint.START,
            // Поиск максимума
            RadixSort.CharacteristicPoint.FIND_MAX,
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP,
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP,
            // Первый разряд (единицы)
            RadixSort.CharacteristicPoint.NEXT_DIGIT,
            RadixSort.CharacteristicPoint.COUNTING_SORT,
            RadixSort.CharacteristicPoint.COUNT_ARRAY_INIT,
            RadixSort.CharacteristicPoint.COUNT_DIGITS,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP,
            RadixSort.CharacteristicPoint.COMPUTE_PREFIX_SUM,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP,
            RadixSort.CharacteristicPoint.COPY_TO_ORIGINAL,
            RadixSort.CharacteristicPoint.COPY_LOOP,
            RadixSort.CharacteristicPoint.COPY_LOOP,
            // Конец (max=7, только 1 разряд)
            RadixSort.CharacteristicPoint.FINISH
        );

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> actual = radixSort.getExecutionTrace();

        assertEquals(referenceSequence, actual,
            "Последовательность характерных точек для [3, 7] должна совпадать с эталонной");
    }

    // ============== Тестирование количества проходов по разрядам ==============

    @Test
    @DisplayName("Проверка количества разрядов для [5, 12] - max=12, 2 разряда")
    void testTwoDigitNumbers() {
        int[] arr = {5, 12};

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> trace = radixSort.getExecutionTrace();

        long nextDigitCount = countOccurrences(trace, RadixSort.CharacteristicPoint.NEXT_DIGIT);
        long countingSortCount = countOccurrences(trace, RadixSort.CharacteristicPoint.COUNTING_SORT);

        // max=12, требуется 2 разряда (единицы и десятки)
        assertEquals(2, nextDigitCount, "Должно быть 2 прохода по разрядам");
        assertEquals(2, countingSortCount, "Должно быть 2 вызова countingSort");
        assertArrayEquals(new int[]{5, 12}, arr, "Массив должен быть отсортирован");
    }

    @Test
    @DisplayName("Проверка количества разрядов для [5, 100, 50] - max=100, 3 разряда")
    void testThreeDigitNumbers() {
        int[] arr = {5, 100, 50};

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> trace = radixSort.getExecutionTrace();

        long nextDigitCount = countOccurrences(trace, RadixSort.CharacteristicPoint.NEXT_DIGIT);
        long countingSortCount = countOccurrences(trace, RadixSort.CharacteristicPoint.COUNTING_SORT);

        // max=100, требуется 3 разряда
        assertEquals(3, nextDigitCount, "Должно быть 3 прохода по разрядам");
        assertEquals(3, countingSortCount, "Должно быть 3 вызова countingSort");
        assertArrayEquals(new int[]{5, 50, 100}, arr, "Массив должен быть отсортирован");
    }

    // ============== Тестирование циклов ==============

    @Test
    @DisplayName("Проверка количества итераций циклов для 5 элементов")
    void testLoopCountsForFiveElements() {
        int[] arr = {53, 12, 7, 89, 34};

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> trace = radixSort.getExecutionTrace();

        // Проверка количества итераций циклов
        assertEquals(5, countOccurrences(trace, RadixSort.CharacteristicPoint.FIND_MAX_LOOP),
            "Цикл findMax должен выполниться 5 раз");

        // max=89, 2 разряда
        assertEquals(2, countOccurrences(trace, RadixSort.CharacteristicPoint.COUNTING_SORT),
            "Должно быть 2 вызова countingSort");

        // Каждый разряд обрабатывает все 5 элементов
        assertEquals(10, countOccurrences(trace, RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP),
            "Цикл countDigits: 2 разряда * 5 элементов = 10");
        assertEquals(10, countOccurrences(trace, RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP),
            "Цикл buildOutput: 2 разряда * 5 элементов = 10");
        assertEquals(10, countOccurrences(trace, RadixSort.CharacteristicPoint.COPY_LOOP),
            "Цикл copy: 2 разряда * 5 элементов = 10");

        assertArrayEquals(new int[]{7, 12, 34, 53, 89}, arr, "Массив должен быть отсортирован");
    }

    @Test
    @DisplayName("Проверка количества итераций для массива [3, 1, 4, 1, 5]")
    void testLoopCountsForReferenceArray() {
        int[] arr = {3, 1, 4, 1, 5};

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> trace = radixSort.getExecutionTrace();

        // Проверка точного количества итераций
        assertEquals(5, countOccurrences(trace, RadixSort.CharacteristicPoint.FIND_MAX_LOOP),
            "Цикл findMax должен выполниться 5 раз (по числу элементов)");

        // max=5, только 1 разряд
        assertEquals(1, countOccurrences(trace, RadixSort.CharacteristicPoint.NEXT_DIGIT),
            "Должен быть 1 проход по разрядам (max=5)");

        assertEquals(5, countOccurrences(trace, RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP),
            "Цикл countDigits должен выполниться 5 раз");
        assertEquals(5, countOccurrences(trace, RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP),
            "Цикл buildOutput должен выполниться 5 раз");
        assertEquals(5, countOccurrences(trace, RadixSort.CharacteristicPoint.COPY_LOOP),
            "Цикл copy должен выполниться 5 раз");

        assertArrayEquals(new int[]{1, 1, 3, 4, 5}, arr, "Массив должен быть отсортирован");
    }

    // ============== Тестирование полной эталонной последовательности ==============

    @Test
    @DisplayName("Сравнение с эталонной последовательностью для [2, 1]")
    void testReferenceSequenceComparison() {
        int[] arr = {2, 1};

        // Построение эталонной последовательности
        List<RadixSort.CharacteristicPoint> referenceSequence = Arrays.asList(
            RadixSort.CharacteristicPoint.START,
            // Поиск максимума (max=2)
            RadixSort.CharacteristicPoint.FIND_MAX,
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP,
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP,
            // Первый разряд (единицы)
            RadixSort.CharacteristicPoint.NEXT_DIGIT,
            RadixSort.CharacteristicPoint.COUNTING_SORT,
            RadixSort.CharacteristicPoint.COUNT_ARRAY_INIT,
            RadixSort.CharacteristicPoint.COUNT_DIGITS,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP,
            RadixSort.CharacteristicPoint.COMPUTE_PREFIX_SUM,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP,
            RadixSort.CharacteristicPoint.COPY_TO_ORIGINAL,
            RadixSort.CharacteristicPoint.COPY_LOOP,
            RadixSort.CharacteristicPoint.COPY_LOOP,
            // Конец (max/10=0, больше разрядов не нужно)
            RadixSort.CharacteristicPoint.FINISH
        );

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> actual = radixSort.getExecutionTrace();

        assertEquals(referenceSequence, actual,
            "Полная последовательность должна совпадать с эталонной для [2, 1]");
    }

    @Test
    @DisplayName("Сравнение с эталонной последовательностью для [3, 1, 4, 1, 5]")
    void testReferenceSequenceForFiveElements() {
        int[] arr = {3, 1, 4, 1, 5};

        // Эталонная последовательность (max=5, только единицы)
        List<RadixSort.CharacteristicPoint> referenceSequence = Arrays.asList(
            RadixSort.CharacteristicPoint.START,
            // Поиск максимума
            RadixSort.CharacteristicPoint.FIND_MAX,
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP, // i=0
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP, // i=1
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP, // i=2
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP, // i=3
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP, // i=4
            // Первый разряд (единицы)
            RadixSort.CharacteristicPoint.NEXT_DIGIT,
            RadixSort.CharacteristicPoint.COUNTING_SORT,
            RadixSort.CharacteristicPoint.COUNT_ARRAY_INIT,
            RadixSort.CharacteristicPoint.COUNT_DIGITS,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP, // i=0
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP, // i=1
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP, // i=2
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP, // i=3
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP, // i=4
            RadixSort.CharacteristicPoint.COMPUTE_PREFIX_SUM,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP, // i=4
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP, // i=3
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP, // i=2
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP, // i=1
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP, // i=0
            RadixSort.CharacteristicPoint.COPY_TO_ORIGINAL,
            RadixSort.CharacteristicPoint.COPY_LOOP, // i=0
            RadixSort.CharacteristicPoint.COPY_LOOP, // i=1
            RadixSort.CharacteristicPoint.COPY_LOOP, // i=2
            RadixSort.CharacteristicPoint.COPY_LOOP, // i=3
            RadixSort.CharacteristicPoint.COPY_LOOP, // i=4
            // Конец
            RadixSort.CharacteristicPoint.FINISH
        );

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> actual = radixSort.getExecutionTrace();

        assertEquals(referenceSequence, actual,
            "Полная последовательность должна совпадать с эталонной для [3, 1, 4, 1, 5]");

        assertArrayEquals(new int[]{1, 1, 3, 4, 5}, arr, "Массив должен быть отсортирован");
    }

    @Test
    @DisplayName("Сравнение с эталонной последовательностью для [10, 2] - 2 разряда")
    void testReferenceSequenceTwoDigits() {
        int[] arr = {10, 2};

        // Эталонная последовательность (max=10, 2 разряда)
        List<RadixSort.CharacteristicPoint> referenceSequence = Arrays.asList(
            RadixSort.CharacteristicPoint.START,
            // Поиск максимума
            RadixSort.CharacteristicPoint.FIND_MAX,
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP,
            RadixSort.CharacteristicPoint.FIND_MAX_LOOP,
            // Первый разряд (единицы)
            RadixSort.CharacteristicPoint.NEXT_DIGIT,
            RadixSort.CharacteristicPoint.COUNTING_SORT,
            RadixSort.CharacteristicPoint.COUNT_ARRAY_INIT,
            RadixSort.CharacteristicPoint.COUNT_DIGITS,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP,
            RadixSort.CharacteristicPoint.COMPUTE_PREFIX_SUM,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP,
            RadixSort.CharacteristicPoint.COPY_TO_ORIGINAL,
            RadixSort.CharacteristicPoint.COPY_LOOP,
            RadixSort.CharacteristicPoint.COPY_LOOP,
            // Второй разряд (десятки)
            RadixSort.CharacteristicPoint.NEXT_DIGIT,
            RadixSort.CharacteristicPoint.COUNTING_SORT,
            RadixSort.CharacteristicPoint.COUNT_ARRAY_INIT,
            RadixSort.CharacteristicPoint.COUNT_DIGITS,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP,
            RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP,
            RadixSort.CharacteristicPoint.COMPUTE_PREFIX_SUM,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP,
            RadixSort.CharacteristicPoint.BUILD_OUTPUT_LOOP,
            RadixSort.CharacteristicPoint.COPY_TO_ORIGINAL,
            RadixSort.CharacteristicPoint.COPY_LOOP,
            RadixSort.CharacteristicPoint.COPY_LOOP,
            // Конец (max/100=0)
            RadixSort.CharacteristicPoint.FINISH
        );

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> actual = radixSort.getExecutionTrace();

        assertEquals(referenceSequence, actual,
            "Полная последовательность должна совпадать с эталонной для [10, 2] (2 разряда)");

        assertArrayEquals(new int[]{2, 10}, arr, "Массив должен быть отсортирован");
    }

    // ============== Тестирование обработки особых случаев ==============

    @Test
    @DisplayName("Проверка уже отсортированного массива [1, 2, 3, 4, 5]")
    void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> trace = radixSort.getExecutionTrace();

        long nextDigitCount = countOccurrences(trace, RadixSort.CharacteristicPoint.NEXT_DIGIT);
        assertEquals(1, nextDigitCount, "Должен быть 1 проход (max=5)");

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr, "Массив должен остаться отсортированным");
    }

    @Test
    @DisplayName("Проверка массива в обратном порядке [5, 4, 3, 2, 1]")
    void testReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> trace = radixSort.getExecutionTrace();

        long nextDigitCount = countOccurrences(trace, RadixSort.CharacteristicPoint.NEXT_DIGIT);
        assertEquals(1, nextDigitCount, "Должен быть 1 проход (max=5)");

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr, "Массив должен быть отсортирован");
    }

    @Test
    @DisplayName("Проверка массива с одинаковыми элементами [5, 5, 5, 5]")
    void testArrayWithDuplicates() {
        int[] arr = {5, 5, 5, 5};

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> trace = radixSort.getExecutionTrace();

        // Проверка количества итераций
        assertEquals(4, countOccurrences(trace, RadixSort.CharacteristicPoint.COUNT_DIGITS_LOOP),
            "Цикл countDigits должен выполниться 4 раза");

        assertArrayEquals(new int[]{5, 5, 5, 5}, arr, "Массив должен остаться неизменным");
    }

    @Test
    @DisplayName("Проверка массива с нулем [0, 5, 3]")
    void testArrayWithZero() {
        int[] arr = {0, 5, 3};

        radixSort.sort(arr);

        assertArrayEquals(new int[]{0, 3, 5}, arr, "Массив должен быть отсортирован с учетом нуля");
    }

    @Test
    @DisplayName("Проверка большого массива (100 элементов)")
    void testLargeArray() {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = 99 - i; // Обратный порядок
        }

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> trace = radixSort.getExecutionTrace();

        // Проверка количества разрядов (max=99, 2 разряда)
        long nextDigitCount = countOccurrences(trace, RadixSort.CharacteristicPoint.NEXT_DIGIT);
        assertEquals(2, nextDigitCount, "Должно быть 2 прохода (max=99)");

        // Проверка сортировки
        for (int i = 0; i < 100; i++) {
            assertEquals(i, arr[i], "Элемент на позиции " + i + " должен быть " + i);
        }
    }

    @Test
    @DisplayName("Проверка массива с однозначными и двузначными числами")
    void testMixedDigitLength() {
        int[] arr = {5, 12, 3, 45, 8};

        radixSort.sort(arr);
        List<RadixSort.CharacteristicPoint> trace = radixSort.getExecutionTrace();

        // max=45, 2 разряда
        long nextDigitCount = countOccurrences(trace, RadixSort.CharacteristicPoint.NEXT_DIGIT);
        assertEquals(2, nextDigitCount, "Должно быть 2 прохода (max=45)");

        assertArrayEquals(new int[]{3, 5, 8, 12, 45}, arr, "Массив должен быть отсортирован");
    }

    // ============== Вспомогательные методы ==============

    private long countOccurrences(List<RadixSort.CharacteristicPoint> trace,
                                   RadixSort.CharacteristicPoint point) {
        return trace.stream().filter(p -> p == point).count();
    }
}
