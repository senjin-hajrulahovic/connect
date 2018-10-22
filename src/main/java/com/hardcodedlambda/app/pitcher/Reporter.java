package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.TestPackage;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;

public class Reporter extends TimerTask {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final List<TestPackage> state;
    private final Clock clock;

    public Reporter(List<TestPackage> state, Clock clock) {
        this.state = state;
        this.clock = clock;
    }

    @Override
    public void run() {
        System.out.print(LocalDateTime.now(clock).format(dateTimeFormatter));
        System.out.print(", ");
        System.out.print("sent packages total: " + state.size());
        System.out.println();
    }
}
