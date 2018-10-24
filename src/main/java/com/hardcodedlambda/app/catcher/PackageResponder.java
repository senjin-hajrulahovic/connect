package com.hardcodedlambda.app.catcher;

import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

@AllArgsConstructor
public class PackageResponder implements Runnable {

    private static boolean keepRunning = true;

    private final NetworkIO networkIO;
    private final ConcurrentLinkedQueue<RequestPackage> receivedPackages;
    private final Clock clock;

    @Override
    public void run() {

        stopAfter(1000);

        while (keepRunning) {
            if (!receivedPackages.isEmpty()) {

                RequestPackage receivedPackage = receivedPackages.poll();

                RequestPackage requestPackage = receivedPackage.copyWithUpdatedTime(LocalDateTime.now(clock));
                networkIO.writeLine(requestPackage.toString());
            }
        }
    }

    // TODO Refactor to not require such mechanism
    void stopAfter(long milis) {
        new Timer().schedule(new TimerTask(){

            @Override
            public void run() {
                keepRunning = false;
            }
        }, milis);
    }
}
