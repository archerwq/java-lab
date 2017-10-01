package com.weibo.keeplooking.algorithm.sorting;

/**
 * Radix sorting, assuming input datas are integers with specific digits count.
 * Time complexity: O(n), space complexity: O(n).
 * 
 * @author Johnny
 * 
 */
public class RadixSort {
    private static int DIGITS_COUNT = 10;
    private static int K = 10; // decimal

    private int[] data;

    public RadixSort(int[] data) {
        this.data = data;
    }

    public void sort() {
        for (int r = 1; r <= DIGITS_COUNT; r++) {
            countingSort(r);
        }
    }

    private void countingSort(int r) {
        int n = data.length;
        int[] radix = new int[n];
        int[] sorted = new int[n];
        int[] count = new int[K];
        int i = 0, k = 0;

        for (i = 0; i < n; i++) {
            radix[i] = radixValue(data[i], r);
        }

        for (k = 0; k < K; k++) {
            count[k] = 0;
        }

        for (i = 0; i < n; i++) {
            count[radix[i]]++;
        }

        for (k = 1; k < K; k++) {
            count[k] += count[k - 1];
        }

        for (i = n - 1; i >= 0; i--) {
            sorted[count[radix[i]] - 1] = data[i];
            count[radix[i]]--;
        }

        for (i = 0; i < n; i++) {
            data[i] = sorted[i];
        }
    }

    /**
     * get the element digit of specific radix
     * 
     * @param element
     *        integer value
     * @param radix
     *        radix of the element
     * @return element digit of radix r
     */
    private int radixValue(int element, int radix) {
        int div = 1;
        for (int i = 1; i < radix; i++) {
            div *= K;
        }
        int temp = element / div;
        return temp % K;
    }

}
