package com.hardcodedlambda.app;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Pitcher {

    private final List<String> logs = new ArrayList<>();
    private final IO networkIO;

    public Pitcher() throws IOException {

        Socket socket = new Socket("localhost", 9092);
        networkIO = new NetworkIO(socket);
    }

    public void pitch() throws Exception {

        Timer timer = new Timer();
        timer.schedule(new LogState(logs), 0, 1000);

        while (true) {
            String line = networkIO.readLine();
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
            networkIO.writeLine("PITCHER: " + Thread.currentThread() + " -:- " + state.get(state.size() - 1));
            System.out.println("WROTE: " + "PITCHER: " + Thread.currentThread() + " -:- " + state.get(state.size() - 1));
        }
    }
}
