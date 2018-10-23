package com.hardcodedlambda.app.pitcher;

import lombok.Builder;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
public class Report {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String DELIMITER = " | ";

    private Clock clock;
    private long sentTotal;
    private long maximumRoundTripTotal;

    private long sentPastSecond;
    private long averageRoundTripPastSecond;
    private long averageToCatcherPastSecond;
    private long averageBackToPitcherPastSecond;


    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(LocalDateTime.now(clock).format(dateTimeFormatter));
        stringBuilder.append(DELIMITER);
        stringBuilder.append("sent packages total: " + sentTotal);
        stringBuilder.append(DELIMITER);
        stringBuilder.append("sent packages past second: " + sentPastSecond);
        stringBuilder.append(DELIMITER);
        stringBuilder.append("average A -> B -> A time past second: " + averageRoundTripPastSecond + " ms");
        stringBuilder.append(DELIMITER);
        stringBuilder.append("max A -> B -> A time: " + maximumRoundTripTotal + " ms");
        stringBuilder.append(DELIMITER);
        stringBuilder.append("average A -> B time: " + averageToCatcherPastSecond + " ms");
        stringBuilder.append(DELIMITER);
        stringBuilder.append("average B -> A time: " + averageBackToPitcherPastSecond + " ms");

        return stringBuilder.toString();
    }
}
