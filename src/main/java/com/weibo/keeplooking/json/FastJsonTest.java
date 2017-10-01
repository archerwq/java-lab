package com.weibo.keeplooking.json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * Fastjson usage demo.
 * 
 * @author Johnny
 *
 */
public class FastJsonTest {

    private static Logger LOGGER = LoggerFactory.getLogger(FastJsonTest.class);

    @Test
    public void testMapToJSON() {
        SomeData data = new SomeData("string property");
        data.put(DataType.TYPE_1, true);
        data.put(DataType.TYPE_2, false);
        data.putOther("key1", 1);
        data.putOther("key2", 2);
        System.out.println(JSON.toJSONString(data)); // WARN: map fields are missing here
        System.out.println(data.toString());
        LOGGER.info("data={}", data);
    }
}


class SomeData implements Serializable {
    private static final long serialVersionUID = 2653246009592993581L;

    private String property;
    private Map<DataType, Object> data;
    private Map<String, Object> otherData;

    public SomeData(String property) {
        this.property = property;
        this.data = new HashMap<DataType, Object>();
        this.otherData = new HashMap<String, Object>();
    }

    public void put(DataType type, Object value) {
        data.put(type, value);
    }

    public void putOther(String key, Object value) {
        otherData.put(key, value);
    }

    public Object get(DataType type) {
        return data.get(type);
    }

    public Object getOther(String key) {
        return otherData.get(key);
    }

    public String getProperty() {
        return property;
    }

    @Override
    public String toString() {
        return String.format("property=%s, data=%s, otherData=%s", property, data, otherData);
    }
}


enum DataType implements Serializable {
    TYPE_1(Boolean.class), TYPE_2(Boolean.class);

    @SuppressWarnings("rawtypes")
    private Class clazz;

    @SuppressWarnings("rawtypes")
    private DataType(Class clazz) {
        this.clazz = clazz;
    }

    public Class<?> getType() {
        return clazz;
    }
}
