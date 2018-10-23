package com.hardcodedlambda.app.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketIO implements NetworkIO {

    private Socket socket = null;
    private BufferedReader reader = null;
    private PrintWriter writer = null;

    public static ServerSocketIO instance(String bind, int port) {
        return new ServerSocketIO(bind, port);
    }

    private ServerSocketIO(String host, int port) {

        try {
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress(host, port));

                socket = serverSocket.accept();

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException ex) {
            System.err.println("Application failed to start with cause: ");
            System.err.println(ex.getLocalizedMessage());
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
}
