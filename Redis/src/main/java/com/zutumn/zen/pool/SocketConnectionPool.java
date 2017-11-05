package com.zutumn.zen.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.net.Socket;

/**
 * Socket Connected Pool
 *
 * @author zhikong.wl
 * 2017-10-17 17:13
 **/
public class SocketConnectionPool {

    private GenericObjectPool<Socket> internalPool;

    public SocketConnectionPool(String host, int port, GenericObjectPoolConfig config) {
        PooledObjectFactory factory = new SocketConnectionFactory(host, port);
        this.internalPool = new GenericObjectPool<Socket>(factory, config);
    }

    public Socket getConnection() throws Exception {
        return internalPool.borrowObject();
    }

    public void releaseConnection(Socket socket) {
        try {
            internalPool.returnObject(socket);
        } catch (Exception e) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public int getNumIdle(){
        return internalPool.getNumIdle();
    }
    public int getNumActive(){
        return internalPool.getNumActive();
    }

    public int getNumWaiters(){
        return internalPool.getNumWaiters();
    }

}
