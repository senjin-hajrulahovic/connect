package com.hardcodedlambda.app.catcher;

import com.hardcodedlambda.app.common.TestPackage;
import com.hardcodedlambda.app.io.NetworkIO;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

// TODO - change name
public class LogConsumer implements Runnable {

    private final NetworkIO networkIO;
    private final ConcurrentLinkedQueue<String> logs;
    private final Clock clock;

    public LogConsumer(NetworkIO networkIO, ConcurrentLinkedQueue<String> logs, Clock clock) {
        this.networkIO = networkIO;
        this.logs = logs;
        this.clock = clock;
    }

    @Override
    public void run() {

        while (true) {
            if (!logs.isEmpty()) {

                String log = logs.poll();

                int packageId = Integer.valueOf(log.split("_")[0]);

                TestPackage pitcherPackage =
                        new TestPackage(packageId, LocalDateTime.now(clock), log.length());

                networkIO.writeLine(pitcherPackage.toString());
                System.out.println(log);
            }
        }
    }
}
