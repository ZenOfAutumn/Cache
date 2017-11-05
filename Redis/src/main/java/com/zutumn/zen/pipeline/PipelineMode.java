package com.zutumn.zen.pipeline;

import com.zutumn.zen.jedis.SimpleJedisFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.concurrent.CountDownLatch;

/**
 * Pipeline Mode
 *
 * @author zhikong.wl
 * 2017-10-20 15:58
 **/
public class PipelineMode {


    public static void pipelineSet(){
        Jedis jedis = SimpleJedisFactory.localSingletonClient();
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < 10; i++) {
            Response<String>  response = pipeline.set("pipeline","1");
            System.out.println(response.get());
        }
        pipeline.sync();
    }


    public static void repeatSet(){
        Jedis jedis = SimpleJedisFactory.localSingletonClient();
        for (int i = 0; i < 10; i++) {
            jedis.set("simple","1");
        }

    }

    public static void order(){
        final CountDownLatch latch = new CountDownLatch(1);
        for(int i = 0;i<10;i++) {
            Thread repeatTask = new Thread() {
                @Override public void run() {
                    try {
                        latch.await();
                        repeatSet();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            repeatTask.start();
        }

        Thread pipelineTask = new Thread(){
            @Override
            public void run() {
                try{
                    latch.await();
                    pipelineSet();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        pipelineTask.start();
        latch.countDown();
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.set("1","22");
        pipelineSet();
    }

}
