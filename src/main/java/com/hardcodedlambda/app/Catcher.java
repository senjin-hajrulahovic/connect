package com.hardcodedlambda.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Catcher {

    private final ConcurrentLinkedQueue<String> logs = new ConcurrentLinkedQueue<>();
    private final IO networkIO;

    public Catcher() throws IOException {
        final Socket server = acceptSocket();
        networkIO = new NetworkIO(server);
    }

    public void listen() throws Exception {

        LogConsumer logConsumer = new LogConsumer(networkIO, logs);

        new Thread(logConsumer).start();

        while (true) {
            String line = networkIO.readLine();
            logs.add(line);
        }
    }

    private Socket acceptSocket() throws IOException {
        return new ServerSocket(9092).accept();
    }
}
