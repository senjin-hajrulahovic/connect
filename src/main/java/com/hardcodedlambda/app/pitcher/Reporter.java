package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.RequestPackage;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;

public class Reporter extends TimerTask {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final List<RequestPackage> sentPackages;
    private final List<RequestPackage> receivedPackages;
    private final Clock clock;

    public Reporter(List<RequestPackage> sentPackages, List<RequestPackage> receivedPackages, Clock clock) {

        this.sentPackages = sentPackages;
        this.receivedPackages = receivedPackages;

        this.clock = clock;
    }

    @Override
    public void run() {
        System.out.print(LocalDateTime.now(clock).format(dateTimeFormatter));
        System.out.print(", ");
        System.out.print("sent packages total: " + sentPackages.size());
        System.out.println();

//        List<RequestPackage> lastSecond =
    }
}
