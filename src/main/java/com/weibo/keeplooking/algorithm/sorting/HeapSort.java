package com.weibo.keeplooking.algorithm.sorting;

/**
 * Heap sorting using max heap based on array. Time complexity: O(n*lgn).
 * 
 * @author Johnny
 */
public class HeapSort {

    private int[] data; // note that datas to be sorted starts at index 1
    private int heapSize;

    public HeapSort(int[] data) {
        this.data = data;
    }

    public void sort() {
        heapSize = data.length - 1;
        if (heapSize <= 1) {
            return;
        }
        buildMaxHeap();
        int temp = 0;
        while (heapSize > 1) {
            temp = data[1];
            data[1] = data[heapSize];
            data[heapSize] = temp;
            heapSize--;
            maxHeapify(1);
        }
    }

    private void buildMaxHeap() {
        for (int i = heapSize / 2; i >= 1; i--) {
            maxHeapify(i);
        }
    }

    /**
     * Maximize the heap, assuming that only the top element violates the nature
     * of max heap. Time complexity: O(lgn).
     * 
     * @param i
     *        top of the heap
     */
    private void maxHeapify(int i) {
        int temp = 0, max = i;
        while (hasLeft(i)) {
            if (data[left(i)] > data[i]) {
                max = left(i);
            }
            if (hasRight(i) && data[right(i)] > data[max]) {
                max = right(i);
            }
            if (max != i) {
                temp = data[max];
                data[max] = data[i];
                data[i] = temp;
                i = max;
            } else {
                break;
            }
        }
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }

    private boolean hasLeft(int i) {
        return 2 * i <= heapSize;
    }

    private boolean hasRight(int i) {
        return 2 * i + 1 <= heapSize;
    }

}
