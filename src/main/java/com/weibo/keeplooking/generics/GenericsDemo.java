package com.weibo.keeplooking.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

/**
 * Generic usage demo.
 * 
 * @author Johnny
 *
 */
public class GenericsDemo {

    class Gen<T> { // or <T extends Object>
        private T ob;

        public Gen(T ob) {
            this.ob = ob;
        }

        public T getOb() {
            return ob;
        }

        public void setOb(T ob) {
            this.ob = ob;
        }

        public void showType() {
            System.out.println("Type of T: " + ob.getClass().getName());
        }
    }

    @Test
    public void testGen() {
        @SuppressWarnings("deprecation")
		Gen<Integer> genInt = new Gen<Integer>(new Integer(5));
        genInt.showType();

        Gen<String> genStr = new Gen<String>("5");
        genStr.showType();
    }

    class CollectionGenFoo<T extends Collection<?>> {
        private T x;

        public CollectionGenFoo(T x) {
            this.x = x;
        }

        public T getX() {
            return x;
        }

        public void setX(T x) {
            this.x = x;
        }

        public void showType() {
            System.out.println("Type of T: " + x.getClass().getName());
        }
    }

    @Test
    public void testGenFoo() {
        CollectionGenFoo<ArrayList<String>> foo = new CollectionGenFoo<ArrayList<String>>(
                new ArrayList<String>());
        foo.showType();
    }

    public void print(List<?> list) {
        for (Object element : list) {
            System.out.println(element.getClass().getName());
        }
    }

    @Test
    public void testPrint() {
        List<String> strList = new ArrayList<String>();
        strList.add("A");
        strList.add("B");
        print(strList);

        List<Integer> intList = new ArrayList<Integer>();
        intList.add(1);
        intList.add(2);
        print(intList);
    }

}
