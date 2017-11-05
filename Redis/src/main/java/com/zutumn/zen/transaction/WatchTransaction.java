package com.zutumn.zen.transaction;

import com.zutumn.zen.jedis.SimpleJedisFactory;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Collections;
import java.util.List;

/**
 * Watch Transaction
 *
 * WATCH->MULTI->...->EXEC
 *
 * @author zhikong.wl
 * 2017-10-19 15:10
 **/
public class WatchTransaction {

    @Test
    public void watchTransaction(){
        try {
            Jedis client4Trans = SimpleJedisFactory.localSingletonClient();
            Jedis client4Simple = SimpleJedisFactory.localSingletonClient();
            try {
                final String key = "simple";

                // watch key
                client4Trans.watch(key);
                Transaction transaction = client4Trans.multi();

                // set/get
                transaction.set(key, "0");
                transaction.get(key);

                // set value before transaction exec
                client4Simple.set(key,"-1");

                // exec
                List<Object> responses = transaction.exec();
                Assert.assertEquals(Collections.emptyList(),responses);

                // after exec
                Assert.assertEquals("-1", client4Simple.get(key));

            } finally {
                client4Simple.close();
                client4Trans.close();
            }
        } catch (JedisConnectionException ex) {
            ex.printStackTrace();
        }

    }
}
