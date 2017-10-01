package com.weibo.keeplooking.zk;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Distributed barrier implemented with ZooKeeper.
 * 
 * @author Johnny
 *
 */
public class DistributedBarrier extends SyncPrimitive {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedBarrier.class);

    private int size;
    private String namePrefix;

    public DistributedBarrier(String address, String root, int size) {
        super(address);
        this.root = root;
        this.size = size;

        // create barrier node
        if (zk != null) {
            try {
                Stat rootStat = zk.exists(root, false);
                if (rootStat == null) {
                    zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                }
            } catch (KeeperException e) {
                LOGGER.error("Failed to create barrier node on ZK.", e);
            } catch (InterruptedException e) {
                LOGGER.error("Failed to create barrier node on ZK.", e);
            }
        }

        // my node name prefix
        try {
            namePrefix = new String(InetAddress.getLocalHost().getCanonicalHostName().toString() + "_");
        } catch (UnknownHostException e) {
            LOGGER.error("Failed to get localhost address.", e);
        }
    }

    /**
     * Join the barrier
     * 
     * @return
     * @throws InterruptedException
     * @throws KeeperException
     */
    public boolean enter() throws KeeperException, InterruptedException {
        // create a child node
        String nodeName = namePrefix + Thread.currentThread().getName();
        String child = zk.create(root + "/" + nodeName, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        LOGGER.info("Child created: {}", child);

        // wait until enough children nodes created
        while (true) {
            synchronized (mutex) {
                List<String> children = zk.getChildren(root, true);
                if (children.size() < size) {
                    mutex.wait(); // notify when watcher triggered
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * Leave the barrier
     * 
     * @return
     * @throws InterruptedException
     * @throws KeeperException
     */
    public boolean leave() throws InterruptedException, KeeperException {
        // delete current child node
        zk.delete(root + "/" + namePrefix + Thread.currentThread().getName(), 0);

        // wait until all children are deleted
        while (true) {
            synchronized (mutex) {
                List<String> children = zk.getChildren(root, true);
                if (children.size() > 0) {
                    mutex.wait();
                } else {
                    return true;
                }
            }
        }
    }
}
