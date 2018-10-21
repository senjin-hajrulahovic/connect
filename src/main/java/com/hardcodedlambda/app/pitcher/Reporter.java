package com.hardcodedlambda.app.pitcher;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;

public class Reporter extends TimerTask {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final List<String> state;
    private final Clock clock;

    public Reporter(List<String> state, Clock clock) {
        this.state = state;
        this.clock = clock;
    }

    @Override
    public void run() {
        System.out.println(LocalDateTime.now(clock).format(dateTimeFormatter) + " Current message count: " + state.size());
    }
}
