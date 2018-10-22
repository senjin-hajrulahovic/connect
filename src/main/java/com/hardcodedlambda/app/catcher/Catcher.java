package com.hardcodedlambda.app.catcher;

import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;
import com.hardcodedlambda.app.io.SocketNetworkIO;

import java.io.IOException;
import java.time.Clock;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Catcher {

    private static final ConcurrentLinkedQueue<RequestPackage> receivedPackages = new ConcurrentLinkedQueue<>();
    private final NetworkIO networkIO;

    public static Catcher instance(CatcherConfig config) throws IOException {

        SocketNetworkIO socketNetworkIO = SocketNetworkIO.instance(config.getBind(), config.getPort(), SocketNetworkIO.Type.SERVER);
        return new Catcher(socketNetworkIO);
    }

    private Catcher(final NetworkIO socketNetworkIO) {
        this.networkIO = socketNetworkIO;
    }

    public void listen() throws Exception {

        LogConsumer logConsumer = new LogConsumer(networkIO, receivedPackages, Clock.systemDefaultZone());

        new Thread(logConsumer).start();

        while (true) {
            RequestPackage requestPackage = RequestPackage.fromString(networkIO.readLine());
            receivedPackages.add(requestPackage);
        }
    }
}
