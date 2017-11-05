package com.zutumn.zen.pool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.net.Socket;

/**
 * Socket App
 *
 * @author zhikong.wl
 * 2017-10-17 17:28
 **/
public class SocketApp {



    public static void main(String[] args) {

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(2);
        config.setMaxTotal(2);
        config.setMinIdle(1);
        final SocketConnectionPool pool = new SocketConnectionPool("localhost", 80, config);
        try {

            Socket socketLeft = pool.getConnection();
            Socket socketRight = pool.getConnection();
            System.out.println("active:" + pool.getNumActive());
            new Thread() {
                @Override public void run() {
                    try {
                        pool.getConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            System.out.println("waiter:" + pool.getNumWaiters());
            pool.releaseConnection(socketLeft);
            System.out.println("active:" + pool.getNumActive());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
