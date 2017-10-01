package com.weibo.keeplooking.pattern.singleton;

/**
 * The standard method to implement Singletons in Java. The technique is known
 * as the initialization on demand holder idiom, is as lazy as possible, and
 * works in all known versions of Java. It takes advantage of language
 * guarantees about class initialization, and will therefore work correctly in
 * all Java-compliant compilers and virtual machines.
 * 
 * @author Johnny
 */
public class SingletonB {

    // Private constructor prevents instantiation from other classes.
    private SingletonB() {}

    /**
     * SingletonHolder is loaded on the first access to
     * SingletonHolder.INSTANCE, not before.
     */
    private static class SingletonHolder {
        static SingletonB INSTANCE = new SingletonB();
    }

    public static SingletonB getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
