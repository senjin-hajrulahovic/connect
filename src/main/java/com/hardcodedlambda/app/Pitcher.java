package com.hardcodedlambda.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Pitcher {

    private List<String> logs = new ArrayList<>();
    private PrintWriter socketWriter;

    public void pitch() throws Exception {

        Timer timer = new Timer();
        timer.schedule(new LogState(logs), 0, 1000);

        Socket client = new Socket("localhost", 9091);

        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

        socketWriter = new PrintWriter(client.getOutputStream(), true);

        while (true) {
            String line = input.readLine();
            System.out.println("READ: Response From Catcher: " + line);
        }
    }

    class LogState extends TimerTask {

        private List<String> state;

        public LogState(List<String> state) {
            this.state = state;
        }

        @Override
        public void run() {
            state.add(LocalDateTime.now().toString());
            socketWriter.println("PITCHER: " + Thread.currentThread() + " -:- " + state.get(state.size() - 1));
            System.out.println("WROTE: " + "PITCHER: " + Thread.currentThread() + " -:- " + state.get(state.size() - 1));
        }
    }
}
