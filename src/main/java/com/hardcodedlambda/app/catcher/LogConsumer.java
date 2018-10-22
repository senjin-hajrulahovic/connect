package com.hardcodedlambda.app.catcher;

import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

// TODO - change name
public class LogConsumer implements Runnable {

    private final NetworkIO networkIO;
    private final ConcurrentLinkedQueue<RequestPackage> receivedPackages;
    private final Clock clock;

    public LogConsumer(NetworkIO networkIO, ConcurrentLinkedQueue<RequestPackage> receivedPackages, Clock clock) {
        this.networkIO = networkIO;
        this.receivedPackages = receivedPackages;
        this.clock = clock;
    }

    @Override
    public void run() {

        while (true) {
            if (!receivedPackages.isEmpty()) {

                RequestPackage receivedPackage = receivedPackages.poll();

                RequestPackage requestPackage = receivedPackage.copyWithUpdatedTime(LocalDateTime.now(clock));

                networkIO.writeLine(requestPackage.toString());
                System.out.println(requestPackage);
            }
        }
    }
}
