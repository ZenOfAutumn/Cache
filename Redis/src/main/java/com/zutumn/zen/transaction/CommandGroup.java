package com.zutumn.zen.transaction;

import redis.clients.jedis.Transaction;

public interface CommandGroup {

    void compose(Transaction transaction);

}
