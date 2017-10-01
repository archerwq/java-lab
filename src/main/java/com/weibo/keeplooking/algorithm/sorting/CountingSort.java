package com.weibo.keeplooking.algorithm.sorting;

/**
 * Counting sorting, assuming input datas are integers in [0, K], K=O(n). Time
 * complexity: O(n), space complexity: O(n+K).
 * 
 * @author Johnny
 * 
 */
public class CountingSort {
    private static int K = 1000;
    private int[] data;

    public CountingSort(int[] data) {
        this.data = data;
    }

    public void sort() {
        int n = data.length;
        int[] sorted = new int[n];
        int[] count = new int[K];

        // fill count array with 0
        for (int k = 0; k < K; k++) {
            count[k] = 0;
        }

        // let count[k] = count of appearance of k in input datas
        for (int i = 0; i < n; i++) {
            count[data[i]]++;
        }

        // let count[k] = count of elements <= k in input datas
        for (int k = 1; k < K; k++) {
            count[k] += count[k - 1];
        }

        for (int i = n - 1; i >= 0; i--) { // from high to low to make stable
            // caculate the order of data[i]
            int orderOfDataI = count[data[i]] - 1;

            sorted[orderOfDataI] = data[i];
            count[data[i]]--;
        }

        for (int i = 0; i < n; i++) {
            data[i] = sorted[i];
        }
    }

}
