package com.weibo.keeplooking.redis;

import java.util.Arrays;

import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.cluster.RedisClusterClient;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;
import com.lambdaworks.redis.cluster.api.sync.RedisClusterCommands;

public class RedisUtil {

	public static void main(String[] args) throws Exception {
		// RedisURI node1 = RedisURI.create("10.13.82.84", 8531);
		RedisURI node2 = RedisURI.create("10.13.82.84", 8532);
		// RedisURI node3 = RedisURI.create("10.20.144.74", 8533);
		RedisURI node4 = RedisURI.create("10.13.82.84", 8534);
		// RedisURI node5 = RedisURI.create("10.20.144.84", 8535);
		RedisURI node6 = RedisURI.create("10.13.82.84", 8536);
		RedisClusterClient client = RedisClusterClient.create(Arrays.asList(node2, node4, node6));
		StatefulRedisClusterConnection<String, String> connection = client.connect();
		RedisClusterCommands<String, String> commands = connection.sync();

		System.out.println(commands.del("am_activity_2_20180328"));
	}

}
