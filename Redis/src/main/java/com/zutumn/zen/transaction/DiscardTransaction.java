package com.zutumn.zen.transaction;

import com.zutumn.zen.jedis.SimpleJedisFactory;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;


/**
 * Redis Discard Transaction
 *
 * MULTI->...->DISCARD
 *
 * @author zhikong.wl
 * 2017-10-19 14:46
 **/
public class DiscardTransaction {

    @Test public void  discardTrans() {
        try {
            Jedis client4Trans = SimpleJedisFactory.localSingletonClient();
            Jedis client4Simple = SimpleJedisFactory.localSingletonClient();
            try {
                final String key = "simple";
                client4Simple.set(key, "-1");
                Transaction transaction = client4Trans.multi();
                transaction.set(key, "0");
                transaction.get(key);
                transaction.set(key, "1");
                transaction.get(key);

                // before exec
                Assert.assertEquals("-1", client4Simple.get(key));

                // exec
                String response =  transaction.discard();
                Assert.assertEquals("OK", response);

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
