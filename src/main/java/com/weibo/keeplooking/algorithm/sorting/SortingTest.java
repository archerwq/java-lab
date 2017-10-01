package com.weibo.keeplooking.algorithm.sorting;

import org.junit.Assert;
import org.junit.Test;

public class SortingTest {

    @Test
    public void testBasicSort() {
        int[] data = new int[] {72, 43, 28, 81, 95, 62, 13, 50, 0, 32};
        BasicSort basicSort = new BasicSort(data);
        basicSort.binaryInsertSort();
        Assert.assertArrayEquals(new int[] {0, 13, 28, 32, 43, 50, 62, 72, 81,
                95}, data);
    }

    @Test
    public void testBucketSort() {
        float[] data = new float[] {0.717f, 0.726f, 0.428f, 0.296f, 0.801f,
                0.964f, 0.923f, 0.673f, 0.185f, 0.517f, 0.001f, 0.319f};
        BucketSort bucketSort = new BucketSort(data);
        bucketSort.sort();
        Assert.assertEquals(0.001f, data[0], 0.0001f);
        Assert.assertEquals(0.428f, data[4], 0.0001f);
        Assert.assertEquals(0.964f, data[11], 0.0001f);
    }

    @Test
    public void testCountingSort() {
        int[] data = new int[] {72, 43, 28, 81, 95, 62, 13, 50, 0, 32};
        CountingSort countingSort = new CountingSort(data);
        countingSort.sort();
        Assert.assertArrayEquals(new int[] {0, 13, 28, 32, 43, 50, 62, 72, 81,
                95}, data);
    }

    @Test
    public void testHeapSort() {
        int[] data = new int[] {-1, 72, 43, 28, 81, 95, 62, 13, 50, 0, 32};
        HeapSort heapSort = new HeapSort(data);
        heapSort.sort();
        Assert.assertArrayEquals(new int[] {-1, 0, 13, 28, 32, 43, 50, 62, 72,
                81, 95}, data);
    }

    @Test
    public void testMergeSort() {
        int[] data = new int[] {72, 43, 28, 81, 95, 62, 13, 50, 0, 32};
        MergeSort mergeSort = new MergeSort(data);
        mergeSort.sort(0, 9);
        Assert.assertArrayEquals(new int[] {0, 13, 28, 32, 43, 50, 62, 72, 81,
                95}, data);
    }

    @Test
    public void testQuickSort() {
        int[] data = new int[] {72, 43, 28, 81, 95, 62, 13, 50, 0, 32};
        QuickSort quickSort = new QuickSort(data);
        quickSort.sort(0, 9);
        Assert.assertArrayEquals(new int[] {0, 13, 28, 32, 43, 50, 62, 72, 81,
                95}, data);
    }

    @Test
    public void testRadixSort() {
        int[] data = new int[] {72, 43, 28, 81, 95, 62, 13, 50, 0, 32};
        RadixSort radixSort = new RadixSort(data);
        radixSort.sort();
        Assert.assertArrayEquals(new int[] {0, 13, 28, 32, 43, 50, 62, 72, 81,
                95}, data);
    }

}
