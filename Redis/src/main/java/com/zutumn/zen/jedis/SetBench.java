package com.zutumn.zen.jedis;

import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.util.Random;
import java.util.Set;

/**
 * Pipeline Bench
 *
 * @author zhikong.wl
 * 2017-10-18 19:52
 **/
public class SetBench {

   public static final Jedis CLIENT = SimpleJedisFactory.localSingletonClient();


    private String[] initBigSet(){
        String[] keys= new String[500];
        int bound = 200000;
        Random random = new Random();

        for(int j = 0;j<500; j++) {
            String key = "order" + j;
            String[] values = new String[100000];
            for (int i = 0; i < 100000; i++) {
                int value = random.nextInt(bound);
                values[i] = String.valueOf(value);
            }
            CLIENT.sadd(key,values);
            keys[j] = key;
        }
        return  keys;
    }

    @Test
    public void bigSetInter(){
        String[] keys = initBigSet();
        Long start = System.nanoTime();
        Set<String> internal =  CLIENT.sinter(keys);
        Long end = System.nanoTime();
        System.out.println("Set Intersection Cost Time(ns): " + (end - start));
        System.out.println(internal);

    }

    @Test
    public void pipeline(){
        Jedis client1 = SimpleJedisFactory.localSingletonClient();
        Jedis client2 = SimpleJedisFactory.localSingletonClient();
        Long start = System.currentTimeMillis();
        for(int j = 0;j<500; j++){
            client1.sadd("order"+j,"13");
        }
        Long end = System.currentTimeMillis();
        System.out.println(end-start);

        Pipeline pipeline = client2.pipelined();
         start = System.currentTimeMillis();

        for(int j = 0;j<500; j++){
            pipeline.sadd("order"+j,"14");
        }
        pipeline.sync();
        end = System.currentTimeMillis();
        System.out.println(end-start);

        start = System.currentTimeMillis();
        Transaction transaction = SimpleJedisFactory.localSingletonClient().multi();
        for (int i = 0; i < 500; i++) {
            transaction.sadd("order"+i,"15");
        }
        transaction.exec();
        end = System.currentTimeMillis();
        System.out.println(end-start);




    }



}
