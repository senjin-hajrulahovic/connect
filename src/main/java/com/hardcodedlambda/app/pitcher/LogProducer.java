package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.TestPackage;
import com.hardcodedlambda.app.io.NetworkIO;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class LogProducer extends TimerTask {

    private final List<String> state;
    private final NetworkIO networkIO;
    private final Clock clock;
    private final int messageSize;

    private AtomicInteger nextAvailablePackageId = new AtomicInteger(0);

    // TODO pass clock to make it testable
    public LogProducer(List<String> state, NetworkIO networkIO, int messageSize, Clock clock) {
        this.state = state;
        this.networkIO = networkIO;
        this.clock = clock;
        this.messageSize = messageSize;
    }

    @Override
    public void run() {

        TestPackage pitcherPackage =
                new TestPackage(nextAvailablePackageId.getAndIncrement(), LocalDateTime.now(clock), messageSize);

        state.add(pitcherPackage.toString());
        networkIO.writeLine(pitcherPackage.toString());
    }
}
