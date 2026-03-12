package lab1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class RadixSortModuleTest {

    @Test
    @DisplayName("Проверка сортировки пустого массива")
    void testEmptyArray() {
        int[] arr = {};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    @DisplayName("Проверка сортировки одного элемента")
    void testSingleElement() {
        int[] arr = {5};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{5}, arr);
    }

    @Test
    @DisplayName("Проверка сортировки двух элементов")
    void testTwoElements() {
        int[] arr = {7, 3};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{3, 7}, arr);
    }

    @Test
    @DisplayName("Проверка сортировки уже отсортированного массива")
    void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    @DisplayName("Проверка сортировки массива в обратном порядке")
    void testReverseOrder() {
        int[] arr = {5, 4, 3, 2, 1};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    @DisplayName("Проверка сортировки с дубликатами")
    void testDuplicates() {
        int[] arr = {5, 5, 3, 3, 1};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 3, 3, 5, 5}, arr);
    }

    @Test
    @DisplayName("Проверка сортировки с нулем")
    void testWithZero() {
        int[] arr = {0, 5, 3};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{0, 3, 5}, arr);
    }

    @Test
    @DisplayName("Проверка сортировки двузначных чисел")
    void testTwoDigitNumbers() {
        int[] arr = {53, 12, 89, 34};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{12, 34, 53, 89}, arr);
    }

    @Test
    @DisplayName("Проверка сортировки трехзначных чисел")
    void testThreeDigitNumbers() {
        int[] arr = {5, 100, 50, 1};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 5, 50, 100}, arr);
    }

    @Test
    @DisplayName("Проверка сортировки большого массива")
    void testLargeArray() {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = 99 - i;
        }
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        for (int i = 0; i < 100; i++) {
            assertEquals(i, arr[i]);
        }
    }

    @Test
    @DisplayName("Проверка сортировки смешанных чисел")
    void testMixedNumbers() {
        int[] arr = {5, 12, 3, 45, 8, 100, 1};
        RadixSort sorter = new RadixSort();
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 3, 5, 8, 12, 45, 100}, arr);
    }
}
