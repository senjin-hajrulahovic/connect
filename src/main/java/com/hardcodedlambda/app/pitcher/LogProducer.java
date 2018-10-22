package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.TestPackage;
import com.hardcodedlambda.app.io.NetworkIO;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class LogProducer extends TimerTask {

    private final List<TestPackage> sentPackages;
    private final NetworkIO networkIO;
    private final Clock clock;
    private final int messageSize;

    private AtomicInteger nextAvailablePackageId = new AtomicInteger(0);

    public LogProducer(List<TestPackage> sentPackages, NetworkIO networkIO, int messageSize, Clock clock) {
        this.sentPackages = sentPackages;
        this.networkIO = networkIO;
        this.clock = clock;
        this.messageSize = messageSize;
    }

    @Override
    public void run() {

        TestPackage pitcherPackage =
                new TestPackage(nextAvailablePackageId.getAndIncrement(), LocalDateTime.now(clock), messageSize);

        sentPackages.add(pitcherPackage);
        networkIO.writeLine(pitcherPackage.toString());
    }
}
