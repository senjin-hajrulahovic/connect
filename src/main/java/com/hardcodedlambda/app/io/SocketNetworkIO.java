package com.hardcodedlambda.app.io;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SocketNetworkIO implements NetworkIO {

    private Socket socket = null;
    private BufferedReader reader = null;
    private PrintWriter writer = null;

    public static SocketNetworkIO instance(String host, int port, Type type) {
        return new SocketNetworkIO(host, port, type);
    }

    private SocketNetworkIO(String host, int port, Type type) {

        System.out.println(host + " " + port);

        try {
            if (type == Type.CLIENT) {
                socket = new Socket(host, port);
            } else {
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress(host, port));

                socket = serverSocket.accept();
            }

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException ex) {
            log.error("Application failed to start with cause: ");
            log.error(ex.getLocalizedMessage());
            System.exit(1);
        }
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            System.err.println(ex.getLocalizedMessage());
            return null;
        }
    }

    public void writeLine(String line) {
        writer.println(line);
    }

    public enum Type {
        SERVER, CLIENT
    }
}
