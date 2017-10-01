package com.weibo.keeplooking.algorithm;

import org.junit.Assert;
import org.junit.Test;

/**
 * Binary search algorithm.
 * 
 * @author Johnny
 */
public class BinarySearch {

    /**
     * Search an element in sorted datas array. Time complexity: O(lgn).
     * 
     * @param data
     *        sorted datas array
     * @param dest
     *        element to search
     * @return index of element <code>dest</code> in data, -1 returned if not
     *         shown
     */
    public int binarySearch(int[] data, int dest) {
        if (data == null) {
            return -1;
        }
        int length = data.length;
        int p = 0, q = length - 1;
        while (p <= q) {
            int r = (p + q) / 2;
            if (data[r] == dest) {
                return r;
            } else if (data[r] > dest) {
                q = r - 1;
            } else if (data[r] < dest) {
                p = r + 1;
            }
        }
        return -1;
    }

    @Test
    public void testBinarySearch() {
        int[] data = {2, 3, 5, 7, 9, 11, 13, 15, 17};
        Assert.assertEquals(0, binarySearch(data, 2));
        Assert.assertEquals(1, binarySearch(data, 3));
        Assert.assertEquals(2, binarySearch(data, 5));
        Assert.assertEquals(3, binarySearch(data, 7));
        Assert.assertEquals(4, binarySearch(data, 9));
        Assert.assertEquals(5, binarySearch(data, 11));
        Assert.assertEquals(6, binarySearch(data, 13));
        Assert.assertEquals(7, binarySearch(data, 15));
        Assert.assertEquals(8, binarySearch(data, 17));
        Assert.assertEquals(-1, binarySearch(data, 1));
    }

}
