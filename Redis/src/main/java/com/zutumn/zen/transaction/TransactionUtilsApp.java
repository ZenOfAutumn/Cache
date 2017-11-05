package com.zutumn.zen.transaction;

import com.zutumn.zen.jedis.SimpleJedisFactory;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Transaction Utility  App
 *
 * @author zhikong.wl
 * 2017-10-20 11:39
 **/
public class TransactionUtilsApp {



     public static void cas() {
        // init
        Jedis initClient = SimpleJedisFactory.localSingletonClient();
        initClient.set("competition","-1");

        int threadNum = 10;
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executor.execute(
                new CasTask(SimpleJedisFactory.localSingletonClient(), "competition", latch));
        }
        try {
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        latch.countDown();


    }





    static class CasTask implements Runnable {

        private Jedis client;

        private String key;

        private CountDownLatch latch;

        CasTask(Jedis client, String key, CountDownLatch latch) {
            this.client = client;
            this.key = key;
            this.latch = latch;
        }

        public void run() {
            try {
                latch.await();
                String dest = Thread.currentThread().getName();
                String expected = client.get(key);
                while (!TransactionUtils.compareAndSwap(client, key, expected, dest)) {
                    expected = client.get(key);
                }
                System.out.println("Cas Succeed." + expected + "->" + dest);
                client.close();
            } catch (JedisConnectionException ex) {
                ex.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        cas();
    }


}
