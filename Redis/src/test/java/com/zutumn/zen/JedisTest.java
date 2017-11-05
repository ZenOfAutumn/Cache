package com.zutumn.zen;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * jedis test
 *
 * @author zhikong.wl
 * 2017-10-18 11:20
 **/
public class JedisTest {


    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.set("code", "java");

        Long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            jedis.set(String.valueOf(i), "value");
        }
        for (int i = 0; i < 100000; i++) {
            jedis.get(String.valueOf(i));
        }

        Long end = System.currentTimeMillis();
        System.out.println(end - start);

        Pipeline pipeline = jedis.pipelined();
        Long sta = System.currentTimeMillis();
        for (int i = 100000; i < 200000; i++) {
            pipeline.set(String.valueOf(i), "value");
        }
        for (int i = 100000; i < 200000; i++) {
            pipeline.get(String.valueOf(i));
        }
        pipeline.sync();
        Long en = System.currentTimeMillis();
        System.out.println("pipeline: " + (en - sta));

        pipeline.multi();


    }

}
