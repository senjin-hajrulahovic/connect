package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BinaryOperator;

@AllArgsConstructor
public class ResponseListener implements Runnable {

    private final Map<Integer, Measurement> measurements;
    private final NetworkIO networkIO;
    private final Clock clock;

    @Override
    public void run() {

        try {

            while (true) {
//            String packageText;
//            while ((packageText = networkIO.readLine()) != null) {

                String packageText = networkIO.readLine();
                RequestPackage requestPackage = RequestPackage.fromString(packageText);

                Measurement measurement = Measurement.builder()
                        .arrivedAtCatcherAt(requestPackage.getTime())
                        .arrivedBackAtPitcherAt(LocalDateTime.now(clock))
                        .build();

                int packageId = requestPackage.getId();

                measurements.merge(packageId, measurement, mergeMeasurements);
            }

        } catch (IOException ex) {
            System.err.println("Failed to load line");
        }
    }

    private static BinaryOperator<Measurement> mergeMeasurements = (sent, received) ->
            Measurement.builder()
                .sentFromPitcherAt(sent.getSentFromPitcherAt())
                .arrivedAtCatcherAt(received.getArrivedAtCatcherAt())
                .arrivedBackAtPitcherAt(received.getArrivedBackAtPitcherAt()).build();
}
