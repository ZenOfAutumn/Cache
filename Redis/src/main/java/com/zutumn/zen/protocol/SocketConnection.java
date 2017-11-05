package com.zutumn.zen.protocol;

import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.util.RedisOutputStream;
import redis.clients.util.SafeEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Socket Connect Redis Server
 *
 * @author zhikong.wl
 * 2017-10-21 23:02
 **/
public class SocketConnection {

    public static final byte DOLLAR_BYTE = '$';
    public static final byte ASTERISK_BYTE = '*';
    public static final byte PLUS_BYTE = '+';
    public static final byte MINUS_BYTE = '-';
    public static final byte COLON_BYTE = ':';

    private static void sendCommand(final RedisOutputStream os, final byte[] command,
        final byte[]... args) {
        try {
            os.write(ASTERISK_BYTE);
            os.writeIntCrLf(args.length + 1);
            os.write(DOLLAR_BYTE);
            os.writeIntCrLf(command.length);
            os.write(command);
            os.writeCrLf();

            for (final byte[] arg : args) {
                os.write(DOLLAR_BYTE);
                os.writeIntCrLf(arg.length);
                os.write(arg);
                os.writeCrLf();
            }
        } catch (IOException e) {
            throw new JedisConnectionException(e);
        }
    }

    public static void connect(String host, int port) throws IOException {

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(host, port));

        RedisOutputStream out = new RedisOutputStream(socket.getOutputStream());
        sendCommand(out, SafeEncoder.encode("SET"), SafeEncoder.encode("foo"),
            SafeEncoder.encode("bar"));
        sendCommand(out, SafeEncoder.encode("GET"), SafeEncoder.encode("foo"));
        out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String sr = in.readLine();
        while (sr != null) {
            System.out.println(sr);
            in.readLine();
        }


    }


    public static void main(String[] args) throws IOException {
        connect("127.0.0.1", 6379);
    }
}
