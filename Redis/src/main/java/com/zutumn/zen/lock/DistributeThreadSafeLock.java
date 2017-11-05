package com.zutumn.zen.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Objects;

/**
 * Distribute Thread Safe Lock
 *
 * @author zhikong.wl
 * 2017-10-23 11:38
 **/
public class DistributeThreadSafeLock {

    private static final Long SINGLE_ARG_COMMAND_SUCCESS_DATA_RESPONSE = 1L;

    private static final Long SINGLE_ARG_COMMAND_FAIL_DATA_RESPONSE = 0L;

    private static final String COMMAND_COMMON_SUCCESS_STRING_RESPONSE = "OK";

    /**
     * try lock specific key within timeout
     *
     * @param client
     * @param lockKey
     * @param timeout(ms)
     * @param interval(ms) polling cycle
     * @param msExist      key exist time
     * @return lock value if succeed, or return null
     */
    public static String tryLock(Jedis client, String lockKey, UniqueIDGenerator generator,
        long timeout, long interval, long msExist) throws InterruptedException {
        String lockValue = generator.generate();
        long endTime = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < endTime) {
            String response = client.set(lockKey, lockValue, "NX", "PX", msExist);
            if (Objects.equals(response, COMMAND_COMMON_SUCCESS_STRING_RESPONSE)) {
                return lockValue;
            }
            Thread.sleep(interval);
        }
        return null;
    }


    /**
     * release lock if the value equals to expected lock value and return true, else return false;
     *
     * @param client
     * @param lockKey
     * @param expectedLockValue
     * @return
     */
    public static boolean releaseLock(Jedis client, String lockKey, String expectedLockValue) {
        client.watch(lockKey);
        String actualValue = client.get(lockKey);
        if (Objects.equals(actualValue, expectedLockValue)) {
            Transaction transaction = client.multi();
            transaction.del(lockKey);
            List<Object> responses = transaction.exec();
            if (responses.size() == 1
                && responses.get(0) == SINGLE_ARG_COMMAND_SUCCESS_DATA_RESPONSE) {
                return true;
            }
            return false;
        }
        client.unwatch();
        return false;
    }

}
