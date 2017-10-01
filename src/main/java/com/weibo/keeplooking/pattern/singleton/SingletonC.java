package com.weibo.keeplooking.pattern.singleton;

/**
 * Singleton implementation with lazy initialization. May have security issue:
 * instance returned to the client without fully initialized. (That can be
 * addressed by declaring INSTANCE as a volatile.)
 * 
 * @author Johnny
 */
public class SingletonC {

    private static SingletonC INSTANCE;

    private SingletonC() {}

    public static SingletonC getInstance() {
        if (INSTANCE == null) {
            synchronized (SingletonC.class) {
                // double check here to avoid duplicated initialization
                if (INSTANCE != null) {
                    INSTANCE = new SingletonC();
                }
            }
        }
        return INSTANCE;
    }

}
