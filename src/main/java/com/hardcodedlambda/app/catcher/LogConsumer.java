package com.hardcodedlambda.app.catcher;

import com.hardcodedlambda.app.io.NetworkIO;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

// TODO - change name
public class LogConsumer implements Runnable {

    private final NetworkIO networkIO;
    private final ConcurrentLinkedQueue<String> logs;

    public LogConsumer(NetworkIO networkIO, ConcurrentLinkedQueue<String> logs) {
        this.networkIO = networkIO;
        this.logs = logs;
    }

    @Override
    public void run() {

        while (true) {
            if (!logs.isEmpty()) {
                String log = logs.poll();

                String messageId = log.split("_")[0];

                networkIO.writeLine(messageId + "_" + LocalDateTime.now().toString());

                System.out.println(log);
            }
        }
    }
}
