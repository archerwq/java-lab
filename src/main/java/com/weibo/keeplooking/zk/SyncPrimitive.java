package com.weibo.keeplooking.zk;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Primitive for higher level synchronize services.
 * 
 * @author Johnny
 *
 */
public class SyncPrimitive implements Watcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncPrimitive.class);

    protected static ZooKeeper zk = null;
    protected static Integer mutex;
    protected String root;

    protected SyncPrimitive(String address) {
        if (zk == null) {
            try {
                LOGGER.info("Starting ZK client...");
                zk = new ZooKeeper(address, 3000, this);
                mutex = new Integer(-1);
                LOGGER.info("ZK client started: {}", zk);
            } catch (IOException e) {
                LOGGER.error("Create ZooKeeper instance failed.", e);
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {
        synchronized (mutex) {
            mutex.notifyAll();
        }
    }

    public void destroy() throws InterruptedException {
        zk.close();
    }
}
