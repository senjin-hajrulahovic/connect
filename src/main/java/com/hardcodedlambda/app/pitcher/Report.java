package com.hardcodedlambda.app.pitcher;

import lombok.Builder;
import lombok.Getter;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
public class Report {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final String DELIMITER = " | ";
    private static final String TIME_UNIT = " ms";

    private Clock clock;
    private long sentTotal;
    private long maximumRoundTripTotal;

    private long sentPastSecond;
    private long averageRoundTripPastSecond;
    private long averageToCatcherPastSecond;
    private long averageBackToPitcherPastSecond;


    public String toString() {

        StringBuilder reportBuilder = new StringBuilder();

        reportBuilder.append(LocalDateTime.now(clock).format(dateTimeFormatter));
        reportBuilder.append(DELIMITER);

        reportBuilder.append("sent total: ");
        reportBuilder.append(validate(sentTotal));
        reportBuilder.append(DELIMITER);

        reportBuilder.append("sent past sec: ");
        reportBuilder.append(validate(sentPastSecond));
        reportBuilder.append(DELIMITER);

        reportBuilder.append("avg A->B->A past sec: ");
        reportBuilder.append(validate(averageRoundTripPastSecond));
        reportBuilder.append(TIME_UNIT);
        reportBuilder.append(DELIMITER);

        reportBuilder.append("max A->B->A total: ");
        reportBuilder.append(validate(maximumRoundTripTotal));
        reportBuilder.append(TIME_UNIT);
        reportBuilder.append(DELIMITER);

        reportBuilder.append("avg A->B past sec: ");
        reportBuilder.append(validate(averageToCatcherPastSecond));
        reportBuilder.append(TIME_UNIT);
        reportBuilder.append(DELIMITER);

        reportBuilder.append("avg B->A past sec: ");
        reportBuilder.append(validate(averageBackToPitcherPastSecond));
        reportBuilder.append(TIME_UNIT);

        return reportBuilder.toString();
    }

    private String validate(long value) {
        return value < 0 ? "N/A" : String.valueOf(value);
    }
}
