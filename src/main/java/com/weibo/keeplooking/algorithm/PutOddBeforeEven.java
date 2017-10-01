package com.weibo.keeplooking.algorithm;

import org.junit.Assert;
import org.junit.Test;

/**
 * Reorder the input datas so as to make all odd numbers come before even.
 * 
 * @author Johnny
 */
public class PutOddBeforeEven {

    public void reorder(int[] data) {
        if (data == null || data.length <= 1) {
            return;
        }

        for (int i = 0, j = 0; j < data.length; j++) {
            if (isOdd(data[j])) {
                if (j - i == 0) {
                    continue;
                }
                if (j - i > 1) {
                    int temp = data[i + 1];
                    data[i + 1] = data[j];
                    data[j] = temp;
                }
                i++;
            }
        }
    }

    private boolean isOdd(int element) {
        return element % 2 != 0;
    }

    @Test
    public void testReorder() {
        int[] data = new int[] {-1, 2, 3, -5, 0, 7, 8};
        reorder(data);
        Assert.assertArrayEquals(new int[] {-1, 3, -5, 7, 0, 2, 8}, data);
    }

}
