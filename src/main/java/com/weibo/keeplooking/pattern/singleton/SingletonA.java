package com.weibo.keeplooking.pattern.singleton;

/**
 * Singleton pattern implementation without lazy initialization.
 * 
 * @author Johnny
 */
public class SingletonA {

    private static SingletonA INSTANCE = new SingletonA();

    private SingletonA() {}

    public static SingletonA getInstance() {
        return INSTANCE;
    }

}
