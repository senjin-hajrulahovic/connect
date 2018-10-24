package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.utils.ClockUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Reporter.class)
public class ReporterTest {

    private static final String FIXED_NOW_STRING = "2018-10-21T16:51:21.223";
    private static Clock FIXED_CLOCK = ClockUtils.getFixedClockFromDateString(FIXED_NOW_STRING);
    private static LocalDateTime FIXED_NOW = LocalDateTime.now(FIXED_CLOCK);
    private static LocalDateTime FIXED_PAST_SECOND = FIXED_NOW.minusSeconds(1);

    @Test
    public void reportShouldHaveNegativeValuesIfMeasurementListIsEmpty() throws Exception {

        Method method = Whitebox.getMethod(Reporter.class, "generateReport", Collection.class, Clock.class);

        Report report = (Report) method.invoke(null, emptyList(), FIXED_CLOCK);

        assertEquals(report.getSentTotal(), 0);
        assertEquals(report.getMaximumRoundTripTotal(), -1);

        assertEquals(report.getSentPastSecond(), 0);
        assertEquals(report.getAverageToCatcherPastSecond(), -1);
        assertEquals(report.getAverageBackToPitcherPastSecond(), -1);
        assertEquals(report.getMaximumRoundTripTotal(), -1);
    }

    @Test
    public void reportShouldHaveSameValuesAsMeasurementIfSingleMeasurement() throws Exception {

        Measurement measurementInPastSecond = Measurement.builder()
                .sentFromPitcherAt(FIXED_PAST_SECOND)
                .arrivedAtCatcherAt(FIXED_NOW.plusSeconds(10))
                .arrivedBackAtPitcherAt(FIXED_NOW.plusSeconds(20))
                .build();

        Method method = Whitebox.getMethod(Reporter.class, "generateReport", Collection.class, Clock.class);

        Report report = (Report) method.invoke(null, singleton(measurementInPastSecond), FIXED_CLOCK);

        assertEquals(report.getSentTotal(), 1);
        assertEquals(report.getMaximumRoundTripTotal(), measurementInPastSecond.millisecondsFromPitcherToPitcher());

        assertEquals(report.getSentPastSecond(), 1);
        assertEquals(report.getAverageToCatcherPastSecond(), measurementInPastSecond.millisecondsFromPitcherToCatcher());
        assertEquals(report.getAverageBackToPitcherPastSecond(),  measurementInPastSecond.millisecondsFromCatcherToPitcher());
        assertEquals(report.getMaximumRoundTripTotal(), measurementInPastSecond.millisecondsFromPitcherToPitcher());
    }

    @Test
    public void reportShouldHaveEmptyValuesIfNoMeasurementInPastSecond() throws Exception {

        Measurement measurementInPastSecond = Measurement.builder()
                .sentFromPitcherAt(FIXED_NOW.minusSeconds(2))
                .arrivedAtCatcherAt(FIXED_NOW.plusSeconds(10))
                .arrivedBackAtPitcherAt(FIXED_NOW.plusSeconds(20))
                .build();

        Method method = Whitebox.getMethod(Reporter.class, "generateReport", Collection.class, Clock.class);

        Report report = (Report) method.invoke(null, singleton(measurementInPastSecond), FIXED_CLOCK);

        assertEquals(report.getSentTotal(), 1);
        assertEquals(report.getSentPastSecond(), 0);
    }

    @Test
    public void reportShouldContainCorrectAverages() throws Exception {

        Measurement firstMeasurementInPastSecond = Measurement.builder()
                .sentFromPitcherAt(FIXED_PAST_SECOND)
                .arrivedAtCatcherAt(FIXED_PAST_SECOND.plusSeconds(10))
                .arrivedBackAtPitcherAt(FIXED_PAST_SECOND.plusSeconds(20))
                .build();

        Measurement secondMeasurementInPastSecond = Measurement.builder()
                .sentFromPitcherAt(FIXED_PAST_SECOND)
                .arrivedAtCatcherAt(FIXED_PAST_SECOND.plusSeconds(20))
                .arrivedBackAtPitcherAt(FIXED_PAST_SECOND.plusSeconds(30))
                .build();

        List<Measurement> measurementInPastSecond = asList(firstMeasurementInPastSecond, secondMeasurementInPastSecond);

        Method method = Whitebox.getMethod(Reporter.class, "generateReport", Collection.class, Clock.class);

        Report report = (Report) method.invoke(null, measurementInPastSecond, FIXED_CLOCK);

        assertEquals(report.getSentTotal(), 2);
        assertEquals(report.getMaximumRoundTripTotal(), 30_000);

        assertEquals(report.getSentPastSecond(), 2);
        assertEquals(report.getAverageToCatcherPastSecond(), 15_000);
        assertEquals(report.getAverageBackToPitcherPastSecond(), 10_000);
        assertEquals(report.getAverageRoundTripPastSecond(), 25_000);
    }

}