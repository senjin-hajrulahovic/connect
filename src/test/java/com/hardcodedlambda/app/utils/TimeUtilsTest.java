package com.hardcodedlambda.app.utils;

import org.junit.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.hardcodedlambda.app.utils.TimeUtils.dateToLocalDateTime;
import static com.hardcodedlambda.app.utils.TimeUtils.isDateInPastSecond;
import static com.hardcodedlambda.app.utils.TimeUtils.truncateToNextSecond;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TimeUtilsTest {

    private static final String FIXED_NOW_STRING = "2018-10-21T16:51:21.223";
    private static Clock FIXED_CLOCK = ClockUtils.getFixedClockFromDateString(FIXED_NOW_STRING);
    private static LocalDateTime FIXED_NOW = LocalDateTime.now(FIXED_CLOCK);

    @Test
    public void testNowShouldNotBeInPastSecondRange() {
        assertFalse(isDateInPastSecond(FIXED_CLOCK, FIXED_NOW));
    }

    @Test
    public void testShouldReturnFalseIfDateIsOutsideOfLastSecondRange() {

        LocalDateTime dateOutsideOfSecondRange = FIXED_NOW.plusSeconds(1);
        assertFalse(isDateInPastSecond(FIXED_CLOCK, dateOutsideOfSecondRange));
    }

    @Test
    public void testShouldReturnTrueIfDateIsInsideOfLastSecondRange() {

        LocalDateTime dateOutsideOfSecondRange = FIXED_NOW.minusSeconds(1);
        assertTrue(isDateInPastSecond(FIXED_CLOCK, dateOutsideOfSecondRange));
    }

    @Test
    public void testTruncateCurrantDateToNextSecond() {

        Date truncatedDate = truncateToNextSecond(FIXED_CLOCK);
        LocalDateTime truncateLocalDateTime = dateToLocalDateTime(truncatedDate);
        LocalDateTime expectedTruncatedLocalDateTime = FIXED_NOW.plusSeconds(1).truncatedTo(ChronoUnit.SECONDS);

        assertEquals(truncateLocalDateTime.getSecond(), expectedTruncatedLocalDateTime.getSecond());
    }

}