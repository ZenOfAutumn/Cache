package com.zutumn.zen;

import com.zutumn.zen.jedis.SimpleJedisFactory;
import com.zutumn.zen.lock.DistributeThreadSafeLock;
import com.zutumn.zen.lock.UUIDGenerator;
import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhikong.wl
 * 2017-10-23 15:21
 **/
public class DistributeLockTest {

static final String LOCK_KEY = "lock";

    static class Task implements  Runnable{

        public void run() {
            Jedis client = SimpleJedisFactory.localSingletonClient();
            while(true){
                try {
                    String value = DistributeThreadSafeLock.tryLock(client, LOCK_KEY, new UUIDGenerator(), 1000,10,500);
                    if(value != null) {
                        System.out.println(Thread.currentThread().getName() + " get lock: " + value);
                        Thread.sleep(300);
                        if(DistributeThreadSafeLock.releaseLock(client,LOCK_KEY,value)){
                            System.out.println(Thread.currentThread().getName() + " release lock: " + value);
                        }else{
                            System.out.println(Thread.currentThread().getName() + " release lock failed: " + value);
                        }
                        return;
                    }else{
                        System.out.println(Thread.currentThread().getName() + " get lock failed ");
                        Thread.sleep(30);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }


    }

    public static void main(String[] args) {

        ExecutorService exe = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            exe.execute(new Task());
        }


    }

}
