package com.weibo.keeplooking.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch demo.
 * 
 * @author Johnny
 */
public class CountDownLatchDemo {

    /**
     * Caculate the total time spent for tasks run in multi-threads
     * concurrently.
     * 
     * @param nThreads
     *        thread count
     * @param task
     *        task to run
     * @return
     * @throws InterruptedException
     */
    public static long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                    } catch (InterruptedException e) {
                        return;
                    }

                    try {
                        task.run();
                    } finally {
                        // always do this in case unchecked exception thrown out
                        // from task.run()
                        endGate.countDown();
                    }
                }
            }.start();
        }

        long startTime = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

}
