package com.hardcodedlambda.app.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketNetworkIO implements NetworkIO {

    private final BufferedReader reader;
    private final PrintWriter writer;

    public static SocketNetworkIO instance(String host, int port, Type type) throws IOException {
        return new SocketNetworkIO(host, port, type);
    }

    private SocketNetworkIO(String host, int port, Type type) throws IOException {

        Socket socket;

        if (type == Type.CLIENT) {
            socket = new Socket(host, port);
        } else {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(host, port));

            socket = serverSocket.accept();
        }

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void writeLine(String line) {
        writer.println(line);
    }

    public enum Type {
        SERVER, CLIENT
    }
}
