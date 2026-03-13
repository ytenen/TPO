package lab1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class RadixSortModuleTest {

    @Test
    @DisplayName("Проверка сортировки пустого массива")
    void testEmptyArray() {
        int[] arr = {};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки одного элемента")
    void testSingleElement() {
        int[] arr = {5};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки двух элементов")
    void testTwoElements() {
        int[] arr = {7, 3};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки уже отсортированного массива")
    void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки массива в обратном порядке")
    void testReverseOrder() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки с дубликатами")
    void testDuplicates() {
        int[] arr = {5, 5, 3, 3, 1};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки с нулем")
    void testWithZero() {
        int[] arr = {0, 5, 3};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки двузначных чисел")
    void testTwoDigitNumbers() {
        int[] arr = {53, 12, 89, 34};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки трехзначных чисел")
    void testThreeDigitNumbers() {
        int[] arr = {5, 100, 50, 1};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки большого массива")
    void testLargeArray() {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = 99 - i;
        }
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки смешанных чисел")
    void testMixedNumbers() {
        int[] arr = {5, 12, 3, 45, 8, 100, 1};
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Проверка сортировки случайного массива")
    void testRandomArray() {
        int[] arr = new int[50];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 1000);
        }
        int[] expected = arr.clone();
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        Arrays.sort(expected);
        assertArrayEquals(expected, arr);
    }
}