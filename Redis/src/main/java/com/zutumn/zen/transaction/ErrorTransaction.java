package com.zutumn.zen.transaction;

import com.zutumn.zen.jedis.SimpleJedisFactory;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.List;

/**
 * Error Transaction
 *
 * @author zhikong.wl
 * 2017-10-19 16:04
 **/
public class ErrorTransaction {

    @Test
    public void errorTransaction(){
        try {
            Jedis client4Trans = SimpleJedisFactory.localSingletonClient();
            Jedis client4Simple = SimpleJedisFactory.localSingletonClient();
            try {
                final String key = "simple";
                Transaction transaction = client4Trans.multi();

                // exec
                transaction.set(key, "0");
                transaction.sadd(key,"1");
                transaction.set(key,"2");

                // exec
                List<Object> responses = transaction.exec();
                Assert.assertEquals("OK",responses.get(0));
                Assert.assertTrue(responses.get(1) instanceof  JedisDataException);
                Assert.assertEquals("OK",responses.get(2));

                // after exec
                Assert.assertEquals("2", client4Simple.get(key));

            } finally {
                client4Simple.close();
                client4Trans.close();
            }
        } catch (JedisConnectionException ex) {
            ex.printStackTrace();
        }

    }
}
