package com.weibo.keeplooking.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Demo for reflection API usage.
 * 
 * @author Johnny
 */
public class ReflectionApi {

    /**
     * Return value for a public field of an object.
     * 
     * @param owner
     *        object
     * @param fieldName
     *        name of the field
     * @return value for the field
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object fieldValue(Object owner, String fieldName)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Class<?> ownerClass = owner.getClass();

        // only returns public field, including field inherit from prarent
        Field field = ownerClass.getField(fieldName);

        return field.get(owner);
    }

    /**
     * Return value for a private field of an object.
     * 
     * @param owner
     *        object
     * @param fieldName
     *        name of the field
     * @return value for the field
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object privateFieldValue(Object owner, String fieldName)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Class<?> ownerClass = owner.getClass();

        // returns both public and private fields, not including field inherit
        // from parent
        Field field = ownerClass.getDeclaredField(fieldName);

        field.setAccessible(true); // suppress java language access checking
        return field.get(owner);
    }

    /**
     * Return value for a static field of a class.
     * 
     * @param className
     *        name of the class
     * @param fieldName
     *        name of the static field
     * @return value for the static field
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object staticFieldValue(String className, String fieldName)
            throws ClassNotFoundException, SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Class<?> ownerClass = Class.forName(className);
        Field field = ownerClass.getField(fieldName);
        return field.get(ownerClass);
    }

    /**
     * Invoke public method of an object.
     * 
     * @param owner
     *        object whose method to be invoked
     * @param methodName
     *        name of the method
     * @param args
     *        parameters of the method
     * @return result of the method invocation
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object invoke(Object owner, String methodName, Object[] args)
            throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Class<?> ownerClass = owner.getClass();
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        Method method = ownerClass.getMethod(methodName, parameterTypes);
        return method.invoke(owner, args);
    }

    /**
     * Invoke private method of an object.
     * 
     * @param owner
     *        object whose method to be invoked
     * @param methodName
     *        name of the method
     * @param args
     *        parameters of the method
     * @return result of the method invocation
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object invokePrivate(Object owner, String methodName,
            Object[] args) throws SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        Class<?> ownerClass = owner.getClass();
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        Method method = ownerClass
                .getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(owner, args);
    }

    /**
     * Invoke static method of a class.
     * 
     * @param className
     *        name of the class
     * @param methodName
     *        name of the static method
     * @param args
     *        parameters of the method
     * @return result of the method invocation
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object invokeStatic(String className, String methodName,
            Object[] args) throws ClassNotFoundException, SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Class<?> ownerClass = Class.forName(className);
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        Method method = ownerClass.getMethod(methodName, parameterTypes);
        return method.invoke(ownerClass, args);
    }

    /**
     * Create an instance of a class by invoking the default constructor.
     * 
     * @param className
     *        name of the class
     * @return an instance of the class
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("deprecation")
	public static Object newInstance(String className)
            throws IllegalAccessException, InstantiationException,
            ClassNotFoundException {
        return Class.forName(className).newInstance();
    }

    /**
     * Create an instance of a class by invoking the specific constructor.
     * 
     * @param className
     *        name of the class
     * @param args
     *        parameters for the constructor
     * @return an instance of the class
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object newInstance(String className, Object[] args)
            throws ClassNotFoundException, SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException {
        Class<?> ownerClass = Class.forName(className);
        Class<?>[] parameterTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }
        Constructor<?> constructor = ownerClass.getConstructor(parameterTypes);
        return constructor.newInstance(args);
    }

}
