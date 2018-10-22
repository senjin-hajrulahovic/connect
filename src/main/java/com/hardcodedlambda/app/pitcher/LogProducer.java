package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class LogProducer extends TimerTask {

    private final List<RequestPackage> sentPackages;
    private final NetworkIO networkIO;
    private final Clock clock;
    private final int messageSize;

    private AtomicInteger nextAvailablePackageId = new AtomicInteger(0);

    public LogProducer(List<RequestPackage> sentPackages, NetworkIO networkIO, int messageSize, Clock clock) {

        this.sentPackages = sentPackages;
        this.networkIO = networkIO;
        this.clock = clock;
        this.messageSize = messageSize;
    }

    @Override
    public void run() {

        RequestPackage pitcherPackage =
                new RequestPackage(nextAvailablePackageId.getAndIncrement(), LocalDateTime.now(clock), messageSize);

        sentPackages.add(pitcherPackage);
        networkIO.writeLine(pitcherPackage.toString());
    }
}
