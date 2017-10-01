package com.weibo.keeplooking.concurrency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Set with fixed bound.
 * 
 * @author Johnny
 */
public class BoundedHashSet<T> {

    private Set<T> set;
    private Semaphore sem;

    public BoundedHashSet(final int n) {
        this.set = new HashSet<T>();
        this.sem = new Semaphore(n);
    }

    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean added = false;
        try {
            added = set.add(o);
            return added;
        } finally {
            if (!added) {
                sem.release();
            }
        }
    }

    public boolean remove(T o) {
        boolean removed = false;
        removed = set.remove(o);
        if (removed) {
            sem.release();
        }
        return removed;
    }

}
