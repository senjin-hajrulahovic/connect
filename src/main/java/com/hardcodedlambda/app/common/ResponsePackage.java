package com.hardcodedlambda.app.common;

import java.time.LocalDateTime;

public class ResponsePackage {

    private int packageId;
    private LocalDateTime arrivedAtCatcherTime;
    private LocalDateTime arrivedBackAtPitcherAtTime;

    public ResponsePackage(int packageId, LocalDateTime arrivedAtCatcherTime, LocalDateTime arrivedBackAtPitcherAtTime) {
        this.packageId = packageId;
        this.arrivedAtCatcherTime = arrivedAtCatcherTime;
        this.arrivedBackAtPitcherAtTime = arrivedBackAtPitcherAtTime;
    }

    public int getPackageId() {
        return packageId;
    }

    public LocalDateTime getArrivedAtCatcherTime() {
        return arrivedAtCatcherTime;
    }

    public LocalDateTime getArrivedBackAtPitcherAtTime() {
        return arrivedBackAtPitcherAtTime;
    }
}
