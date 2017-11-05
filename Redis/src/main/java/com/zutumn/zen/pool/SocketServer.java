package com.zutumn.zen.pool;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Socket Server
 *
 * @author zhikong.wl
 * 2017-10-17 17:22
 **/
public class SocketServer {

    private static AtomicInteger connected;

    static {
        connected = new AtomicInteger(0);
    }

    public static void server(int port) throws InterruptedException, IOException {

        final ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                final Socket socket = serverSocket.accept();
                System.out.println("Socket Connected:" + connected.addAndGet(1));
                // new thread to handle connection
                new Thread(new Runnable() {
                    public void run() {
                        OutputStream out;
                        try {
                            try {
                                out = socket.getOutputStream();
                                out.write("Hello,World!".getBytes("UTF-8"));
                                out.flush();
                            } finally {
                                socket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            serverSocket.close();
        }
    }

    public static void main(String[] args) throws  Exception {
        server(80);
    }
}
