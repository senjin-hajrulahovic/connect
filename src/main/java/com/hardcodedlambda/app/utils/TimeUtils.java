package com.hardcodedlambda.app.utils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {

    public static  boolean isDateInPastSecond(Clock clock, LocalDateTime date) {

        LocalDateTime pastSecond = LocalDateTime.now(clock).minusSeconds(1);

        LocalDateTime secondStart = pastSecond.truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime secondEnd = secondStart.plusSeconds(1);

        return !date.isBefore(secondStart) && date.isBefore(secondEnd);
    }
}
