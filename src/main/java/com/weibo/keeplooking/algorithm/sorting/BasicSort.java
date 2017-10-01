package com.weibo.keeplooking.algorithm.sorting;

/**
 * Basic sorting algorithms with time complexity of O(n^2).
 * 
 * @author Johnny
 */
public class BasicSort {

    private int[] data;

    public BasicSort(int[] data) {
        this.data = data;
    }

    /**
     * Insertion sorting, every loop insert an element into the sorted
     * collection.
     */
    public void insertSort() {
        int key = 0, j = 0;
        for (int i = 1; i < data.length; i++) {
            key = data[i];
            for (j = i - 1; j >= 0 && data[j] > key; j--) {
                data[j + 1] = data[j];
            }
            data[j + 1] = key;
        }
    }

    /**
     * Bubble sorting: every loop bubble the smallest element to the right
     * place.
     */
    public void bubbleSort() {
        int temp = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = data.length - 1; j > i; j--) {
                if (data[j] < data[j - 1]) {
                    temp = data[j - 1];
                    data[j - 1] = data[j];
                    data[j] = temp;
                }
            }
        }
    }

    public void exchangeSort() {
        int temp = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[i]) {
                    temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                }
            }
        }
    }

    public void selectSort() {
        int minIndex = 0;
        int temp = 0;
        for (int i = 0; i < data.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }
            temp = data[i];
            data[i] = data[minIndex];
            data[minIndex] = temp;
        }
    }

    /**
     * Optimize insertion sort with binary searching. For comparation time
     * complexity is O(n*lgn), for element movement is O(n^2).
     */
    public void binaryInsertSort() {
        for (int i = 1; i < data.length; i++) {
            int low = 0, high = i - 1;
            while (high >= low) {
                int middle = (high + low) / 2;
                if (data[i] < data[middle]) {
                    high = middle - 1;
                } else {
                    low = middle + 1;
                }
            }

            int insertIndex = low;
            int temp = data[i];
            for (int j = i; j > insertIndex; j--) {
                data[j] = data[j - 1];
            }
            data[insertIndex] = temp;
        }
    }

}
