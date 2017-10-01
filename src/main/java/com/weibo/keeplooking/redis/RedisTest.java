package com.weibo.keeplooking.redis;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.SetArgs;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.embedded.RedisServer;

import java.io.IOException;

public class RedisTest {
    private RedisServer redisServer;
    private RedisCommands<String, String> commands;

    @Before
    public void setup() throws IOException {
        redisServer = new RedisServer(6379);
        redisServer.start();

        RedisClient client = RedisClient.create("redis://localhost:6379");
        StatefulRedisConnection<String, String> connection = client.connect();
        commands = connection.sync();
    }

    @Test
    public void testGet() {
        commands.hgetall("+N7dRvjUXK6jp4akagS0wg==")
                .forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    @Test
    public void testBasicGetSet() {
        commands.set("user:100", "Qiang Wang");
        Assert.assertEquals("Qiang Wang", commands.get("user:100"));
        Assert.assertNull(commands.get("user:200"));
    }

    @Test
    public void testKeyWithExpiration() throws InterruptedException {
        commands.set("user:100", "Qiang Wang", SetArgs.Builder.px(5000));
        Assert.assertEquals("Qiang Wang", commands.get("user:100"));
        Thread.sleep(6000);
        Assert.assertNull(commands.get("user:100"));
    }

    @Test
    public void testList() {
        Assert.assertEquals(3, commands.rpush("mylist", "A", "B", "C").longValue());
        Assert.assertArrayEquals(new String[] {"A", "B", "C"}, commands.lrange("mylist", 0, -1).toArray());
        commands.rpop("mylist");
        Assert.assertArrayEquals(new String[] {"A", "B"}, commands.lrange("mylist", 0, -1).toArray());
    }

    @After
    public void teardown() {
        redisServer.stop();
    }

}
