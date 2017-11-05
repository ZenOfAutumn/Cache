package com.zutumn.zen.jedis;

import redis.clients.jedis.Jedis;

/**
 * Redis Common Config
 *
 * @author zhikong.wl
 * 2017-10-18 19:51
 **/
public class SimpleJedisFactory {

    public static final String LOCAL_SINGLETON_HOST = "127.0.0.1";

    public static final int LOCAL_SINGLETON_PORT = 6379;

    public static Jedis localSingletonClient(){
        return new Jedis(LOCAL_SINGLETON_HOST,LOCAL_SINGLETON_PORT);
    }
}
