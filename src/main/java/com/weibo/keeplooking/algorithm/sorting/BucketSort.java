package com.weibo.keeplooking.algorithm.sorting;

import java.util.ArrayList;
import java.util.List;

/**
 * Bucket sorting, assuming input datas are floats in [0, 1), uniform
 * distributed is best. Average time complexity: O(n), space complexity: O(n).
 * 
 * @author Johnny
 * 
 */
public class BucketSort {

    private float[] data;

    public BucketSort(float[] data) {
        this.data = data;
    }

    public void sort() {
        int i = 0, count = data.length;

        // initialize the buckets
        List<List<Float>> buckets = new ArrayList<List<Float>>();
        for (i = 0; i < count; i++) {
            buckets.add(new ArrayList<Float>());
        }

        // fill datas into buckets
        for (i = 0; i < count; i++) {
            buckets.get((int) Math.floor(data[i] * count)).add(data[i]);
        }

        // sort each bucket
        int index = 0;
        for (i = 0; i < count; i++) {
            List<Float> bucket = buckets.get(i);
            insertSort(bucket);
            int bucketSize = bucket.size();
            for (int j = 0; j < bucketSize; j++) {
                data[index + j] = bucket.get(j);
            }
            index += bucketSize;
        }
    }

    private void insertSort(List<Float> bucket) {
        for (int i = 1; i < bucket.size(); i++) {
            Float f = bucket.get(i);
            int j = 0;
            for (j = i - 1; j >= 0 && bucket.get(j) > f; j--) {
                bucket.set(j + 1, bucket.get(j));
            }
            bucket.set(j + 1, f);
        }
    }

}
