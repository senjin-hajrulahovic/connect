package com.hardcodedlambda.app.utils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtils {

    public static  boolean isDateInPastSecond(Clock clock, LocalDateTime date) {

        LocalDateTime pastSecond = LocalDateTime.now(clock).minusSeconds(1);

        LocalDateTime secondStart = pastSecond.truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime secondEnd = secondStart.plusSeconds(1);

        return !date.isBefore(secondStart) && date.isBefore(secondEnd);
    }

    public static Date truncateToNextSecond(Clock clock) {
        LocalDateTime firstRunTime = LocalDateTime.now(clock).plusSeconds(1).truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime zdt = firstRunTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
