package com.hardcodedlambda.app.pitcher;

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

    private AtomicInteger messageId = new AtomicInteger(0);

    // TODO pass clock to make it testable
    public LogProducer(List<String> state, NetworkIO networkIO, Clock clock) {
        this.state = state;
        this.networkIO = networkIO;
        this.clock = clock;
    }

    @Override
    public void run() {
        String message = generateMessage(LocalDateTime.now(clock));

        state.add(message);
        networkIO.writeLine(message);
    }

    private String generateMessage(LocalDateTime time) {
        String message = messageId.getAndIncrement() + "_" + time.toString();
        return message;
    }
}
