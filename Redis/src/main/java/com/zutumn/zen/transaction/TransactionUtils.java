package com.zutumn.zen.transaction;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Collections;
import java.util.List;

/**
 * Transaction Utils
 *
 * @author zhikong.wl
 * 2017-10-19 16:34
 **/
public class TransactionUtils {

    /**
     * Compare And Swap
     *
     * @param client
     * @param key
     * @param expected
     * @param dest
     * @return
     */
    public static boolean compareAndSwap(Jedis client, String key, String expected, String dest) {
        client.watch(key);
        String actual = client.get(key);
        if (!actual.equals(expected)) {
            client.unwatch();
            return false;
        } else {
            Transaction transaction = client.multi();
            transaction.set(key, dest);
            List<Object> reponse = transaction.exec();
            if (Collections.emptyList().equals(reponse)) {
                return false;
            }
            if (reponse.get(0).equals("OK")) {
                return true;
            } else {
                return false;
            }
        }
    }


    public static void transExec(Jedis client, CommandGroup group) {
        transExec(client, group);
    }

    public static List<Object> transExec(Jedis client, CommandGroup group, String... watchKeys) {
        if (watchKeys != null) {
            client.watch(watchKeys);
        }
        Transaction transaction = client.multi();
        group.compose(transaction);
        List<Object> responses = transaction.exec();
        return responses;
    }



}
