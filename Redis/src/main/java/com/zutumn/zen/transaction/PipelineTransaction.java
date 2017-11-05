package com.zutumn.zen.transaction;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

/**
 * Combine Transaction And Pipeline
 *
 * @author zhikong.wl
 * 2017-10-20 16:54
 **/
public class PipelineTransaction {

    private Jedis client;


    public static void main(String[] args) {

        Jedis jedis = new Jedis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("12345","12");
        pipeline.sync();
        pipeline.syncAndReturnAll();


    }



}
