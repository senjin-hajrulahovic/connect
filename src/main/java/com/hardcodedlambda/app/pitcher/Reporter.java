package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import static com.hardcodedlambda.app.utils.TimeUtils.isDateInPastSecond;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class Reporter extends TimerTask {

    private final Map<Integer, Measurement> measurements;
    private final Clock clock;

    @Override
    public void run() {

        List<Measurement> measurementsFromPastSecond = measurements.values().stream()
                .filter(measurement -> isDateInPastSecond(clock, measurement.getSentFromPitcherAt()))
                .collect(toList());


        // TODO exclude values if -1
        long maxRoundTripTime = measurements.values().stream()
                .filter(Measurement::completedRoundTrip)
                .mapToLong(Measurement::milisecondsFromPitcherToPitcher).max().orElse(-1);


        // TODO exclude values if -1
        long averageRoundTripTimeInPastSecond = (long) measurementsFromPastSecond.stream()
                .filter(Measurement::completedRoundTrip)
                .mapToLong(Measurement::milisecondsFromPitcherToPitcher)
                .average().orElse(-1);

        // TODO exclude values if -1
        long averageTimeFromPitcherToCatcherInPastSecond = (long) measurementsFromPastSecond.stream()
                .filter(Measurement::completedRoundTrip)
                .mapToLong(Measurement::milisecondsFromPitcherToCatcher)
                .average().orElse(-1);

        // TODO exclude values if -1
        long averageTimeFromCatcherToPitcherInPastSecond = (long) measurementsFromPastSecond.stream()
                .filter(Measurement::completedRoundTrip)
                .mapToLong(Measurement::milisecondsFromPCatcherToPitcher)
                .average().orElse(-1);

        Report report = Report.builder()
                .clock(clock)
                .sentTotal(measurements.size())
                .maximumRoundTripTotal(maxRoundTripTime)

                .sentPastSecond(measurementsFromPastSecond.size())
                .averageRoundTripPastSecond(averageRoundTripTimeInPastSecond)
                .averageToCatcherPastSecond(averageTimeFromPitcherToCatcherInPastSecond)
                .averageBackToPitcherPastSecond(averageTimeFromCatcherToPitcherInPastSecond)
                .build();

        System.out.println(report);
    }
}
