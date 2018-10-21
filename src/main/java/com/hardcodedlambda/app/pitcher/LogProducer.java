package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.io.NetworkIO;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class LogProducer extends TimerTask {

    private static final String ID_FORMAT = "%010d";
    private static final char DELIMITER = '_';

    private final List<String> state;
    private final NetworkIO networkIO;
    private final Clock clock;
    private final int messageSize;

    private AtomicInteger messageId = new AtomicInteger(0);

    // TODO pass clock to make it testable
    public LogProducer(List<String> state, NetworkIO networkIO, int messageSize, Clock clock) {
        this.state = state;
        this.networkIO = networkIO;
        this.clock = clock;
        this.messageSize = messageSize;
    }

    @Override
    public void run() {
        String message = generateMessage(LocalDateTime.now(clock));

        state.add(message);
        networkIO.writeLine(message);
    }

    private String generateMessage(LocalDateTime time) {

        String dateString = time.toString();

        String randomText = randomStringOfLength(MESSAGE_ID_LENGTH - 1 - dateString.length());

        String id = padLeftWithZeros(messageId.getAndIncrement(), MESSAGE_ID_LENGTH);

        return id + DELIMITER + randomText;
    }

    private String randomStringOfLength(int length) {

        char[] charArray = new char[length];
        Arrays.fill(charArray, 'a');
        return String.valueOf(charArray);
    }

    private String padLeftWithZeros(Integer number) {

        String format = "%0" + MESSAGE_ID_LENGTH +"d";
        return String.format(format, number);
    }
}
