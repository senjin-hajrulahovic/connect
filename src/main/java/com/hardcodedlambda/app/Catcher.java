package com.hardcodedlambda.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Catcher {

    private final ConcurrentLinkedQueue<String> logs = new ConcurrentLinkedQueue<>();
    private final IO networkIO;

    public static Catcher instance(CatcherConfig config) throws IOException {

        NetworkIO networkIO = new NetworkIO(config.host, config.port, NetworkIO.Type.SERVER);
        return new Catcher(networkIO);
    }

    public Catcher(final NetworkIO networkIO) {
        this.networkIO = networkIO;
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

        SocketAddress address = new InetSocketAddress("localhost", 9092);
        ServerSocket server = new ServerSocket();

        server.bind(address);
        return server.accept();
    }
}
