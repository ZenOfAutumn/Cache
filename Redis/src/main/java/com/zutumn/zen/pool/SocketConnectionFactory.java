package com.zutumn.zen.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;

import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Socket Connection Factory
 *
 * @author zhikong.wl
 * 2017-10-17 16:46
 **/
public class SocketConnectionFactory extends BasePooledObjectFactory<Socket> {

    private InetSocketAddress address;

    public SocketConnectionFactory(String host,int port){
        address = new InetSocketAddress(host,port);
    }

    @Override public Socket create() throws Exception {
        Socket socket = new Socket();
        socket.connect(address);
        return socket;
    }

    @Override public PooledObject<Socket> wrap(Socket obj) {
        return new PooledSocket(obj);
    }

    @Override public void destroyObject(PooledObject<Socket> p) throws Exception {
        p.getObject().close();
    }

    @Override public boolean validateObject(PooledObject<Socket> p) {
        Socket socket = p.getObject();
        if(socket.isConnected()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

    }

}
