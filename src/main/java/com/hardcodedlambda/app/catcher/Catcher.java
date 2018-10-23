package com.hardcodedlambda.app.catcher;

import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;
import com.hardcodedlambda.app.io.SocketNetworkIO;

import java.time.Clock;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Catcher {

    private static final ConcurrentLinkedQueue<RequestPackage> receivedPackages = new ConcurrentLinkedQueue<>();
    private final NetworkIO networkIO;

    public static Catcher instance(CatcherConfig config) {

        SocketNetworkIO socketNetworkIO = SocketNetworkIO.instance(config.getBind(), config.getPort(), SocketNetworkIO.Type.SERVER);
        return new Catcher(socketNetworkIO);
    }

    private Catcher(final NetworkIO socketNetworkIO) {
        this.networkIO = socketNetworkIO;
    }

    public void listen() {

        PackageResponder packageResponder = new PackageResponder(networkIO, receivedPackages, Clock.systemDefaultZone());

        new Thread(packageResponder).start();

        String line;
        while ((line = networkIO.readLine()) != null) {
            RequestPackage requestPackage = RequestPackage.fromString(line);
            receivedPackages.add(requestPackage);
        }
    }
}
