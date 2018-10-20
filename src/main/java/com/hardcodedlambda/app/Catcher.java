package com.hardcodedlambda.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Catcher {

    private final ConcurrentLinkedQueue<String> logs = new ConcurrentLinkedQueue<>();
    private final NetworkIO networkIO;

    public Catcher() throws IOException {
        final Socket server = new ServerSocket(9092).accept();

        networkIO = new NetworkIO(server);
    }

    public void listen() throws Exception {

        final Runnable runnable = () -> {
            System.out.println(Thread.currentThread());
            while (true) {
                if (!logs.isEmpty()) {
                    String log = logs.poll();
                    networkIO.writeLine("CATCHER: " + log);
                    System.out.println(log);
                }
            }
        };

        new Thread(runnable).start();

        while (true) {
            String line = networkIO.readLine();
            logs.add(line);
        }
    }
}
