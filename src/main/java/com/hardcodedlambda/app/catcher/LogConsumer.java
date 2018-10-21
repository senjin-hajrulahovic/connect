package com.hardcodedlambda.app.catcher;

import com.hardcodedlambda.app.io.NetworkIO;

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
                networkIO.writeLine("CATCHER: " + log);
                System.out.println(log);
            }
        }
    }
}
