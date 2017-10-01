package com.weibo.keeplooking.algorithm;

import org.junit.Assert;
import org.junit.Test;

/**
 * Order statistic algorithm: assuming all the datas are distinct. Expected time
 * complexity: O(n).
 * 
 * @author Johnny
 * 
 */
public class OrderStatistic {

    private int[] data;

    public void setData(int[] data) {
        this.data = data;
    }

    /**
     * Return the element of specific order.
     * 
     * @param p
     *        start index of input datas
     * @param r
     *        end index of input datas
     * @param i
     *        the order
     * 
     * @return the element of specific order
     */
    public int select(int p, int r, int i) {
        int q = partition(p, r);
        int orderQ = q - p + 1;
        if (orderQ == i) {
            return data[q];
        } else if (orderQ > i) {
            return select(p, q - 1, i);
        } else {
            return select(q + 1, r, i - q);
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

    @Test
    public void testSelect() {
        int[] data = new int[] {72, 43, 28, 81, 95, 62, 13, 50, 0, 32};
        OrderStatistic orderStatistic = new OrderStatistic();
        orderStatistic.setData(data);
        Assert.assertEquals(0, orderStatistic.select(0, 9, 1));
        Assert.assertEquals(13, orderStatistic.select(0, 9, 2));
        Assert.assertEquals(28, orderStatistic.select(0, 9, 3));
        Assert.assertEquals(32, orderStatistic.select(0, 9, 4));
        Assert.assertEquals(43, orderStatistic.select(0, 9, 5));
        Assert.assertEquals(50, orderStatistic.select(0, 9, 6));
        Assert.assertEquals(62, orderStatistic.select(0, 9, 7));
        Assert.assertEquals(72, orderStatistic.select(0, 9, 8));
        Assert.assertEquals(81, orderStatistic.select(0, 9, 9));
        Assert.assertEquals(95, orderStatistic.select(0, 9, 10));
    }

}
