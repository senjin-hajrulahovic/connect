package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.io.NetworkIO;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

public class ResponseListener implements Runnable {

    private final NetworkIO networkIO;
    private final List<String> logs;
    private final Clock clock;

    public ResponseListener(List<String> logs, NetworkIO networkIO, Clock clock) {
        this.logs = logs;
        this.networkIO = networkIO;
        this.clock = clock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String line = networkIO.readLine();
                String messageId = line.split("_")[0];
                String arrivedAtCatcherString = line.split("_")[1];

                LocalDateTime arrivedAtCatcher = LocalDateTime.parse(arrivedAtCatcherString);
                LocalDateTime receivedAt = LocalDateTime.now(clock);

                logs.stream().filter(l -> l.startsWith(messageId + "_")).findFirst()
                        .ifPresent(l -> {

                            System.out.println("ORIGINAL MESSAGE FOUND");
                            System.out.println("sent at:            " + l.split("_")[1]);
                            System.out.println("arrived at catcher: " + arrivedAtCatcher);
                            System.out.println("back to pitcher at: " + receivedAt);
                        });


                System.out.println("response from catcher: " + messageId + "_" + receivedAt);
            } catch (IOException ex) {
                System.err.println("Failed to load line");
            }


        }
    }
}
