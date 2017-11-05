package com.zutumn.zen.transaction;

import redis.clients.jedis.Transaction;

/**
 * @author zhikong.wl
 * 2017-10-28 21:10
 **/
public class SimpleCommandGroup implements CommandGroup {

    public void compose(Transaction transaction) {
        transaction.set("monitor","true");
    }
}
