package com.weibo.keeplooking.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    static interface Greeting {
        public void sayHi();
    }

    static class Hello implements Greeting {
        @Override
        public void sayHi() {
            System.out.println("How are you doing?");
        }
    }

    static class GreetingProxy implements InvocationHandler {
        private Greeting origin;

        public Greeting bind(Greeting origin) {
            this.origin = origin;
            // Returns an instance of a proxy class for the specified interfaces that dispatches
            // method invocations to the specified invocation handler.
            return (Greeting) Proxy.newProxyInstance(DynamicProxyTest.class.getClassLoader(), new Class<?>[] {Greeting.class}, this);
        }

        /**
         * Processes a method invocation on a proxy instance and returns the result. This method
         * will be invoked on an invocation handler when a method is invoked on a proxy instance
         * that it is associated with.
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Welcome!");
            return method.invoke(origin, args);
        }
    }

    public static void main(String[] args) {
        Greeting greeting = new GreetingProxy().bind(new Hello());
        greeting.sayHi();
    }
}
