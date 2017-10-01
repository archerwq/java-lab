package com.weibo.keeplooking.algorithm.sorting;

import java.util.Random;

/**
 * Quick sorting, suitable for large set of input datas. Average time
 * complexity: O(n*lgn), Worst:O(n^2).
 * 
 * @author Johnny
 * 
 */
public class QuickSort {

    private int[] data;
    private Random ran = new Random();

    public QuickSort(int[] data) {
        this.data = data;
    }

    /**
     * Partition datas into left part and right part, then do so to the two
     * parts recursively.
     * 
     * @param p
     *        start index of datas to be sorted
     * @param r
     *        end index of datas to be sorted
     */
    public void sort(int p, int r) {
        if (p < r) {
            int q = randomPartition(p, r);
            sort(p, q - 1);
            sort(q + 1, r);
        }
    }

    /**
     * Partition datas into two parts separated by a middle element with all the
     * elements of right part larger than it and all that of left part smaller
     * than it.
     * 
     * @param p
     *        start index of datas to be partitioned
     * @param r
     *        end index of datas to be partitioned
     * @return index of the middle element
     */
    private int partition(int p, int r) {
        int i = p, temp = 0;
        for (int j = p; j < r; j++) {
            if (data[j] <= data[r]) {
                if (i != j) {
                    temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                }
                i++;
            }
        }
        temp = data[i];
        data[i] = data[r];
        data[r] = temp;
        return i;
    }

    /**
     * Randmized partition to make the two parts have equal count of elements as
     * far as possible.
     * 
     * @param p
     *        start index of datas to be partitioned
     * @param r
     *        end index of datas to be partitioned
     * @return index of the middle element
     */
    private int randomPartition(int p, int r) {
        int n = r - p + 1;
        int i = ran.nextInt(n);
        int temp = data[r];
        data[r] = data[p + i];
        data[p + i] = temp;
        return partition(p, r);
    }

}
