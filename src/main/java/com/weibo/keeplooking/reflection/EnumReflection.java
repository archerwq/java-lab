package com.weibo.keeplooking.reflection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class EnumReflection {

    @Test
    public void allValues() {
        Class<? extends Code> errorCodeClass = ErrorCode.class;
        Code[] errorCodes = errorCodeClass.getEnumConstants();
        Assert.assertEquals(3, errorCodes.length);
        Arrays.asList(errorCodes).forEach(code -> {
            System.out.println(code);
            Assert.assertTrue(ErrorCode.class == code.getClass());
        });
    }

    @Test
    public void specifyValue() {
        Assert.assertEquals(ErrorCode.NotFound, getValue(ErrorCode.class, "NotFound"));
        Assert.assertEquals(ErrorCode.class, getValue(ErrorCode.class, "NotFound").getClass());
    }

    @Test
    public void enumFunction() {
        System.out.println(ErrorCode.values().length);
        System.out.println(ErrorCode.valueOf("NotFound"));
        System.out.println(ErrorCode.class);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public Enum getValue(Class enumClass, String name) {
        return Enum.valueOf(enumClass, name);
    }

}


enum ErrorCode implements Code {
    NotFound(404), AuthenticationFail(401), Timeout(501);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    @Override
    public int getNumber() {
        return code;
    }
}


interface Code {
    public int getNumber();
}

