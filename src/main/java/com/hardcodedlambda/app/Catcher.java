package com.hardcodedlambda.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Catcher {

    private static final ConcurrentLinkedQueue<String> logs = new ConcurrentLinkedQueue<>();

    public void listen() throws Exception {

        final ServerSocket listener = new ServerSocket(9091);

        final Socket server = listener.accept();

        final BufferedReader input = new BufferedReader(new InputStreamReader(server.getInputStream()));

        final PrintWriter out = new PrintWriter(server.getOutputStream(), true);

        final Runnable runnable = () -> {
            System.out.println(Thread.currentThread());
            while (true) {
                if (!logs.isEmpty()) {
                    String log = logs.poll();
                    out.println("CATCHER: " + log);
                    System.out.println(log);
                }
            }
        };

        new Thread(runnable).start();

        while (true) {
            String line = input.readLine();
            logs.add(line);
        }
    }
}
