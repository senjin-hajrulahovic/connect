package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TimerTask;

@AllArgsConstructor
public class Reporter extends TimerTask {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final Map<Integer, Measurement> measurements;
    private final Clock clock;

    @Override
    public void run() {
        System.out.print(LocalDateTime.now(clock).format(dateTimeFormatter));
        System.out.print(", ");
        System.out.print("sent packages total: " + measurements.size());
        System.out.println();

//        List<RequestPackage> lastSecond =
    }
}
