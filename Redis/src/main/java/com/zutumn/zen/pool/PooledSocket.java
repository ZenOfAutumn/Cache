package com.zutumn.zen.pool;

import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.net.Socket;

/**
 * Pooled Socket Connection
 *
 * @author zhikong.wl
 * 2017-10-17 17:04
 **/
public class PooledSocket extends DefaultPooledObject<Socket> {

    /**
     * Create a new instance that wraps the provided object so that the pool can
     * track the state of the pooled object.
     *
     * @param object The object to wrap
     */
    public PooledSocket(Socket object) {
        super(object);
    }

}
