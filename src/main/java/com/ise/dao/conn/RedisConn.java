package com.ise.dao.conn;

import redis.clients.jedis.Jedis;

public class RedisConn {
	public static void main(String[] args) {
		String str = "192.168.137.30";
		@SuppressWarnings("resource")
		Jedis jedis = new Jedis(str);
		jedis.auth("123456");
		jedis.set("hello", "world");
		String result = jedis.get("hello");
		System.out.println(result);
	}
}
