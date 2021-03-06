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

    public long millisecondsFromPitcherToCatcher() {
        return sentFromPitcherAt.until(arrivedAtCatcherAt, ChronoUnit.MILLIS);
    }

    public long millisecondsFromCatcherToPitcher() {
        return arrivedAtCatcherAt.until(arrivedBackAtPitcherAt, ChronoUnit.MILLIS);
    }

    public long millisecondsFromPitcherToPitcher() {
        return millisecondsFromPitcherToCatcher() + millisecondsFromCatcherToPitcher();
    }

}
