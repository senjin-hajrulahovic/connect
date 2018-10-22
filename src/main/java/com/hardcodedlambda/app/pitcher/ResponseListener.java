package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class ResponseListener implements Runnable {

    private final NetworkIO networkIO;
    private final List<RequestPackage> receivedPackages;
    private final Clock clock;

    public ResponseListener(List<RequestPackage> receivedPackages, NetworkIO networkIO, Clock clock) {
        this.networkIO = networkIO;
        this.receivedPackages = receivedPackages;
        this.clock = clock;
    }

    @Override
    public void run() {

        // TODO use public Timer(boolean isDaemon) ??
        while (true) {
            try {
                String packageText = networkIO.readLine();

                RequestPackage requestPackage = RequestPackage.fromString(packageText);


                LocalDateTime receivedAt = LocalDateTime.now(clock);

//                logs.stream().filter(l -> l.startsWith(messageId + "_")).findFirst()
//                        .ifPresent(l -> {
//
//                            System.out.println("            -----------             ");
//                            System.out.println("sent at:            " + l.split("_")[1]);
//                            System.out.println("arrived at catcher: " + arrivedAtCatcher);
//                            System.out.println("back to pitcher at: " + receivedAt);
//                            System.out.println("            -----------             ");
//                        });


//                System.out.println("response from catcher: " + line);
            } catch (IOException ex) {
                System.err.println("Failed to load line");
            }


        }
    }
}
