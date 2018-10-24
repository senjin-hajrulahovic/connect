package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.util.*;

import static com.hardcodedlambda.app.utils.TimeUtils.isDateInPastSecond;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Slf4j
public class Reporter extends TimerTask {

    private final Map<Integer, Measurement> measurements;
    private final Clock clock;

    @Override
    public void run() {

        Report report = generateReport(measurements.values(), clock);
        log.info(report.toString());
    }

    private static Report generateReport(Collection<Measurement> measurementList, Clock clock) {

        List<Measurement> completedMeasurementsFromPastSecond = measurementList.stream()
                .filter(measurement -> isDateInPastSecond(clock, measurement.getSentFromPitcherAt()))
                .filter(Measurement::completedRoundTrip)
                .collect(toList());

        long maxRoundTripTime = measurementList.stream()
                .filter(Measurement::completedRoundTrip)
                .mapToLong(Measurement::millisecondsFromPitcherToPitcher)
                .max().orElse(-1);


        long averageRoundTripTimeInPastSecond = (long) completedMeasurementsFromPastSecond.stream()
                .mapToLong(Measurement::millisecondsFromPitcherToPitcher)
                .average().orElse(-1);

        long averageTimeFromPitcherToCatcherInPastSecond = (long) completedMeasurementsFromPastSecond.stream()
                .mapToLong(Measurement::millisecondsFromPitcherToCatcher)
                .average().orElse(-1);

        long averageTimeFromCatcherToPitcherInPastSecond = (long) completedMeasurementsFromPastSecond.stream()
                .mapToLong(Measurement::millisecondsFromCatcherToPitcher)
                .average().orElse(-1);

        return Report.builder()
                .clock(clock)
                .sentTotal(measurementList.size())
                .maximumRoundTripTotal(maxRoundTripTime)

                .sentPastSecond(completedMeasurementsFromPastSecond.size())
                .averageRoundTripPastSecond(averageRoundTripTimeInPastSecond)
                .averageToCatcherPastSecond(averageTimeFromPitcherToCatcherInPastSecond)
                .averageBackToPitcherPastSecond(averageTimeFromCatcherToPitcherInPastSecond)
                .build();
    }
}
