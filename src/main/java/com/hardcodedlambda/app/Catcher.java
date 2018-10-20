package com.hardcodedlambda.app;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Catcher {

    private final ConcurrentLinkedQueue<String> logs = new ConcurrentLinkedQueue<>();
    private final NetworkIO networkIO;

    public static Catcher instance(CatcherConfig config) throws IOException {

        SocketNetworkIO socketNetworkIO = new SocketNetworkIO(config.host, config.port, SocketNetworkIO.Type.SERVER);
        return new Catcher(socketNetworkIO);
    }

    public Catcher(final SocketNetworkIO socketNetworkIO) {
        this.networkIO = socketNetworkIO;
    }

    public void listen() throws Exception {

        LogConsumer logConsumer = new LogConsumer(networkIO, logs);

        new Thread(logConsumer).start();

        while (true) {
            String line = networkIO.readLine();
            logs.add(line);
        }
    }
}
