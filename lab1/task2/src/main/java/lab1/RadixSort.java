package lab1;

import java.util.ArrayList;
import java.util.List;

public class RadixSort {

    public enum CharacteristicPoint {
        START,              // Algorithm start
        FIND_MAX,           // Finding maximum element
        FIND_MAX_LOOP,      // Loop through array to find max
        COUNTING_SORT,      // Counting sort for a digit
        COUNT_ARRAY_INIT,   // Initialize count array
        COUNT_DIGITS,       // Count occurrences of digits
        COUNT_DIGITS_LOOP,  // Loop to count digits
        COMPUTE_PREFIX_SUM, // Compute cumulative count
        BUILD_OUTPUT,       // Build output array
        BUILD_OUTPUT_LOOP,  // Loop to build output
        COPY_TO_ORIGINAL,   // Copy sorted values back
        COPY_LOOP,          // Loop to copy values
        NEXT_DIGIT,         // Move to next digit position
        FINISH              // Algorithm finish
    }

    private final List<CharacteristicPoint> executionTrace;
    private boolean tracingEnabled;

    public RadixSort() {
        this.executionTrace = new ArrayList<>();
        this.tracingEnabled = false;
    }

    /**
     * Enable or disable execution tracing
     */
    public void setTracingEnabled(boolean enabled) {
        this.tracingEnabled = enabled;
        if (enabled) {
            this.executionTrace.clear();
        }
    }

    /**
     * Get the execution trace
     */
    public List<CharacteristicPoint> getExecutionTrace() {
        return new ArrayList<>(executionTrace);
    }

    /**
     * Record a characteristic point hit
     */
    private void trace(CharacteristicPoint point) {
        if (tracingEnabled) {
            executionTrace.add(point);
        }
    }

    /**
     * Main radix sort method
     */
    public void sort(int[] arr) {
        trace(CharacteristicPoint.START);

        if (arr == null || arr.length == 0) {
            trace(CharacteristicPoint.FINISH);
            return;
        }

        // Find maximum to determine number of digits
        trace(CharacteristicPoint.FIND_MAX);
        int max = findMax(arr);

        // Perform counting sort for every digit
        for (int exp = 1; max / exp > 0; exp *= 10) {
            trace(CharacteristicPoint.NEXT_DIGIT);
            countingSortByDigit(arr, exp);
        }

        trace(CharacteristicPoint.FINISH);
    }

    /**
     * Find maximum element in array
     */
    private int findMax(int[] arr) {
        int max = arr[0];
        trace(CharacteristicPoint.FIND_MAX_LOOP);

        for (int i = 1; i < arr.length; i++) {
            trace(CharacteristicPoint.FIND_MAX_LOOP);
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }

    /**
     * Counting sort based on the digit represented by exp
     */
    private void countingSortByDigit(int[] arr, int exp) {
        trace(CharacteristicPoint.COUNTING_SORT);

        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        // Initialize count array
        trace(CharacteristicPoint.COUNT_ARRAY_INIT);

        // Count occurrences of each digit
        trace(CharacteristicPoint.COUNT_DIGITS);
        for (int i = 0; i < n; i++) {
            trace(CharacteristicPoint.COUNT_DIGITS_LOOP);
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }

        // Compute prefix sum (cumulative count)
        trace(CharacteristicPoint.COMPUTE_PREFIX_SUM);
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build output array (placing elements in sorted order)
        trace(CharacteristicPoint.BUILD_OUTPUT);
        for (int i = n - 1; i >= 0; i--) {
            trace(CharacteristicPoint.BUILD_OUTPUT_LOOP);
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        // Copy output array back to original array
        trace(CharacteristicPoint.COPY_TO_ORIGINAL);
        for (int i = 0; i < n; i++) {
            trace(CharacteristicPoint.COPY_LOOP);
            arr[i] = output[i];
        }
    }
}
