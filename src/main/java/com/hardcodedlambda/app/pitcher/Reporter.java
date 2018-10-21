package com.hardcodedlambda.app.pitcher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;

public class Reporter extends TimerTask {

    private final List<String> state;

    public Reporter(List<String> state) {
        this.state = state;
    }

    @Override
    public void run() {
        System.out.println(LocalDateTime.now() + " Current message count: " + state.size());
    }
}
