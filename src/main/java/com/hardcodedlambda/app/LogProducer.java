package com.hardcodedlambda.app;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;

public class LogProducer extends TimerTask {

    private final List<String> state;
    private final IO networkIO;

    public LogProducer(List<String> state, IO networkIO) {
        this.state = state;
        this.networkIO = networkIO;
    }

    @Override
    public void run() {
        state.add(LocalDateTime.now().toString());
        networkIO.writeLine("PITCHER: " + Thread.currentThread() + " -:- " + state.get(state.size() - 1));
        System.out.println("WROTE: " + "PITCHER: " + Thread.currentThread() + " -:- " + state.get(state.size() - 1));
    }
}