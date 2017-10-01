package com.weibo.keeplooking.concurrency;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for concurrency.
 * 
 * @author Johnny
 */
public class ConcurrencyTest {

    @Test
    public void testTimeTasks() throws InterruptedException {

        long duration = CountDownLatchDemo.timeTasks(20, new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Assert.assertTrue(duration > (long) 1 * 1000 * 1000 * 1000);
        Assert.assertTrue(duration < (long) 20 * 1000 * 1000 * 1000);
    }

    @Test
    public void testBounedHashSet() throws InterruptedException {
        final BoundedHashSet<String> set = new BoundedHashSet<String>(3);
        set.add("A");
        set.add("B");
        set.add("C");
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    set.remove("A");
                    set.remove("B");
                } catch (InterruptedException ignored) {
                }
            }
        }.start();
        set.add("D");
        Assert.assertTrue(set.add("E"));
    }

}
