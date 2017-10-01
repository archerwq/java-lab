package com.weibo.keeplooking.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo for ExcecutorService usage.
 * 
 * @author Johnny
 */
public class ExecutorDemo {

    private static final Logger logger = LoggerFactory
            .getLogger(ExecutorDemo.class);

    private static final int BATCH_SIZE = 5;

    public void process(List<String> dataList) {
        long start = System.currentTimeMillis();

        int total = dataList.size();
        int batchCount = (total + BATCH_SIZE - 1) / BATCH_SIZE;

        ExecutorService executor = Executors.newFixedThreadPool(batchCount);
        CountDownLatch latch = new CountDownLatch(batchCount);

        for (int i = 0; i < batchCount; i++) {
            // caculate the boundary of sub task
            int begin = i * BATCH_SIZE;
            int end = (total - begin) > BATCH_SIZE ? begin + BATCH_SIZE : total;

            List<String> subList = dataList.subList(begin, end);
            Runnable subTask = new SubTask(i + 1, subList, latch);
            executor.submit(subTask);
        }

        // wait here till all the sub tasks finish
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("latch.await() was interrupted.", e);
        }

        executor.shutdown();
        long end = System.currentTimeMillis();
        logger.info("Process data done: total {}, cost {} ms.", total, end
                - start);
    }

    private class SubTask implements Runnable {
        private int taskId;
        private List<String> subList;
        private CountDownLatch latch;

        public SubTask(final int taskId, final List<String> subList,
                final CountDownLatch latch) {
            this.taskId = taskId;
            this.subList = subList;
            this.latch = latch;
        }

        @Override
        public void run() {
            logger.info("Task {} starting...", taskId);
            long start = System.currentTimeMillis();
            try {
                doProcess(taskId, subList);
            } catch (Exception e) {
                logger.error("Failed to process sub task {}.", taskId, e);
            } finally {
                latch.countDown(); // do this even exception occurs
            }
            long end = System.currentTimeMillis();
            logger.info("Task {} done: total {}, cost {} ms.", taskId,
                    subList.size(), end - start);
        }

        private void doProcess(int taskId, List<String> dataList) {
            for (String data : dataList) {
                logger.info("Processing -> [taskId={}, data={}]", taskId, data);
            }
        }
    }

    @Test
    public void test() {
        List<String> inputList = new ArrayList<String>();
        inputList.add("A");
        inputList.add("B");
        inputList.add("C");
        inputList.add("D");
        inputList.add("E");
        inputList.add("F");
        inputList.add("G");
        inputList.add("H");
        inputList.add("I");
        inputList.add("J");
        inputList.add("K");
        inputList.add("L");
        inputList.add("M");
        inputList.add("N");

        process(inputList);
    }
}
