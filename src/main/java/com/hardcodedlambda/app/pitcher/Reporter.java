package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.common.RequestPackage;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class Reporter extends TimerTask {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final Map<Integer, Measurement> measurements;
    private final Clock clock;

    @Override
    public void run() {

        LocalDateTime pastSecond = LocalDateTime.now(clock).minusSeconds(1);

        List<Measurement> measurementsFromPastSecond = measurements.values().stream()
                .filter(measurement -> isMeasurementSentInSecondRange(pastSecond, measurement)).collect(toList());

        long averageRoundTripTimeInPastSecond = (long) measurementsFromPastSecond.stream()
                .filter(Measurement::completedRoundTrip)
                .mapToLong(Measurement::milisFromPitcgerToPitcher)
                .average().orElse(-1);

        long maxRoundTripTime = measurements.values().stream()
                .filter(Measurement::completedRoundTrip)
                .mapToLong(Measurement::milisFromPitcgerToPitcher).max().orElse(-1);

        long averageTimeFromPitcherToCatcherInPastSecond = (long) measurementsFromPastSecond.stream()
                .filter(Measurement::completedRoundTrip)
                .mapToLong(Measurement::milisFromPitcherToCatcher)
                .average().orElse(-1);

        long averageTimeFromCatcherToPitcherInPastSecond = (long) measurementsFromPastSecond.stream()
                .filter(Measurement::completedRoundTrip)
                .mapToLong(Measurement::milisFromPCatcherToPitcher)
                .average().orElse(-1);




        System.out.print(LocalDateTime.now(clock).format(dateTimeFormatter));
        System.out.print(", ");
        System.out.print("sent packages total: " + measurements.size());
        System.out.print(", ");
        System.out.print("sent packages past second: " + measurementsFromPastSecond.size());
        System.out.print(", ");
        System.out.print("average A -> B -> A time past second: " + averageRoundTripTimeInPastSecond + " ms");
        System.out.print(", ");
        System.out.print("max A -> B -> A time: " + maxRoundTripTime + " ms");
        System.out.print(", ");
        System.out.print("average A -> B time: " + averageTimeFromPitcherToCatcherInPastSecond + " ms");
        System.out.print(", ");
        System.out.print("average B -> A time: " + averageTimeFromCatcherToPitcherInPastSecond + " ms");
        System.out.println();



//        System.out.println("- --------------------------   SECOND START  -----------------");

//        measurements.entrySet().stream().filter((e) -> isMeasurementSentInSecondRange(pastSecond, e.getValue())).forEach(System.out::println);

//        System.out.println("- --------------------------   SECOND END  -----------------");

    }

    public boolean isMeasurementSentInSecondRange(LocalDateTime now, Measurement measurement) {

        LocalDateTime sentFromPitcherAt = measurement.getSentFromPitcherAt();


        LocalDateTime secondStart = now.truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime secondEnd = secondStart.plusSeconds(1);

        return !sentFromPitcherAt.isBefore(secondStart) && sentFromPitcherAt.isBefore(secondEnd);
    }


}
