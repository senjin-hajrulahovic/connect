package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class PackageEmitter extends TimerTask {

    private static AtomicInteger nextAvailablePackageId = new AtomicInteger(0);

    private final Map<Integer, Measurement> measurements;
    private final NetworkIO networkIO;
    private final int messageSize;
    private final Clock clock;

    @Override
    public void run() {

        int packageId = nextAvailablePackageId.getAndIncrement();
        LocalDateTime packageSentAt = LocalDateTime.now(clock);

        RequestPackage pitcherPackage = new RequestPackage(packageId, packageSentAt, messageSize);
        Measurement measurement = Measurement.builder()
                .sentFromPitcherAt(packageSentAt).build();

        measurements.put(packageId, measurement);
        networkIO.writeLine(pitcherPackage.toString());
    }
}
