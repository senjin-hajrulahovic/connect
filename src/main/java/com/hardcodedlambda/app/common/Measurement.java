package com.hardcodedlambda.app.common;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Builder
@Getter
@ToString
public class Measurement {

    private LocalDateTime sentFromPitcherAt;
    private LocalDateTime arrivedAtCatcherAt;
    private LocalDateTime arrivedBackAtPitcherAt;

    public boolean completedRoundTrip() {
        return sentFromPitcherAt != null && arrivedAtCatcherAt != null && arrivedBackAtPitcherAt != null;
    }

    public long milisFromPitcherToCatcher() {
        return sentFromPitcherAt.until(arrivedAtCatcherAt, ChronoUnit.MILLIS);
    }

    public long milisFromPCatcherToPitcher() {
        return arrivedAtCatcherAt.until(arrivedBackAtPitcherAt, ChronoUnit.MILLIS);
    }

    public long milisFromPitcgerToPitcher() {
        return milisFromPitcherToCatcher() + milisFromPCatcherToPitcher();
    }

}
