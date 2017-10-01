package com.weibo.keeplooking.algorithm.sorting;

/**
 * Merge sorting. Time complexity: O(n*lgn), space complexity: O(n).
 * 
 * @author Johnny
 * 
 */
public class MergeSort {

    private int[] data;

    public MergeSort(int[] data) {
        this.data = data;
    }

    /**
     * Divide and conquer. T(n) = 2*T(n/2)+O(n).
     * 
     * @param p
     *        start index of datas to be sorted
     * @param r
     *        end index of datas to be sorted
     */
    public void sort(int p, int r) {
        if (p < r) {
            int q = (r + p) / 2;
            sort(p, q);
            sort(q + 1, r);
            merge(p, q, r);
        }
    }

    /**
     * Merge two sorted array into one.
     * 
     * @param p
     *        start index of left sorted array
     * @param q
     *        end index of left sorted array
     * @param r
     *        end index of right sorted array
     */
    private void merge(int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - (q + 1) + 1;
        int[] left = new int[n1 + 1];
        int[] right = new int[n2 + 1];
        for (int i = 0; i < n1; i++) {
            left[i] = data[p + i];
        }
        left[n1] = Integer.MAX_VALUE;
        for (int j = 0; j < n2; j++) {
            right[j] = data[q + 1 + j];
        }
        right[n2] = Integer.MAX_VALUE;

        int i = 0, j = 0;
        for (int k = p; k <= r; k++) {
            if (left[i] <= right[j]) {
                data[k] = left[i];
                i++;
            } else {
                data[k] = right[j];
                j++;
            }
        }
    }

}
