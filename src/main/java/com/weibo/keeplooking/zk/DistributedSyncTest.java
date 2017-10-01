package com.weibo.keeplooking.zk;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test cases for distributed synchronize service implemented with Zookeeper.
 * 
 * @author Johnny
 *
 */
public class DistributedSyncTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedSyncTest.class);

    @Test
    public void testBarrier() {
        int workerCount = 5;
        final DistributedBarrier barrier = new DistributedBarrier("10.86.40.66:2181", "/test", workerCount + 1);

        for (int i = 0; i < workerCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        long start = System.currentTimeMillis();
                        String name = Thread.currentThread().getName();
                        LOGGER.info("Thread {} starting...", name);
                        barrier.enter();
                        LOGGER.info("Thread {} enter the barrier.", name);
                        Thread.sleep(5000);
                        long end = System.currentTimeMillis();
                        LOGGER.info("Thread {} done, cost {} ms.", name, end - start);
                        barrier.leave();
                    } catch (Exception e) {
                        LOGGER.error("Error occured.", e);
                    }
                }
            }).start();
        }

        try {
            long start = System.currentTimeMillis();
            barrier.enter();
            LOGGER.info("Main thread enter the barrier.");
            barrier.leave();
            long end = System.currentTimeMillis();
            long cost = end - start;
            LOGGER.info("All the workers done, cost {} ms.", cost);
            Assert.assertTrue(cost > 5000 && cost < 25000);
        } catch (Exception e) {
            LOGGER.error("Error occured.", e);
        } finally {
            try {
                barrier.destroy();
            } catch (InterruptedException e) {
                LOGGER.error("Error occured.", e);
            }
        }
    }

}
