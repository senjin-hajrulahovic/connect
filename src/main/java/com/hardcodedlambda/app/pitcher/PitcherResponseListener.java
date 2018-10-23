package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BinaryOperator;

@AllArgsConstructor
public class PitcherResponseListener implements Runnable {

    private final Map<Integer, Measurement> measurements;
    private final NetworkIO networkIO;
    private final Clock clock;

    @Override
    public void run() {

        String packageText;
        while ((packageText = networkIO.readLine()) != null) {

            RequestPackage requestPackage = RequestPackage.fromString(packageText);

            Measurement measurement = Measurement.builder()
                    .arrivedAtCatcherAt(requestPackage.getTime())
                    .arrivedBackAtPitcherAt(LocalDateTime.now(clock))
                    .build();

            int packageId = requestPackage.getId();

            measurements.merge(packageId, measurement, mergeMeasurements);
        }
    }

    private static BinaryOperator<Measurement> mergeMeasurements = (sent, received) ->

            Measurement.builder()
                .sentFromPitcherAt(sent.getSentFromPitcherAt())
                .arrivedAtCatcherAt(received.getArrivedAtCatcherAt())
                .arrivedBackAtPitcherAt(received.getArrivedBackAtPitcherAt()).build();
}
