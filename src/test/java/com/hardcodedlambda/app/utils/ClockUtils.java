package com.hardcodedlambda.app.utils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class ClockUtils {

    public static Clock getFixedClockFromDateString(String dateString) {

        LocalDateTime fixedNow = LocalDateTime.parse(dateString);
        return Clock.fixed(fixedNow.toInstant(OffsetDateTime.now().getOffset()), ZoneOffset.systemDefault());
    }
}
