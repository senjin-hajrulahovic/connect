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

        // TODO use public Timer(boolean isDaemon) ??
        while (true) {
            try {
                String packageText = networkIO.readLine();
                RequestPackage requestPackage = RequestPackage.fromString(packageText);

                Measurement measurement = Measurement.builder()
                        .arrivedAtCatcherTime(requestPackage.getTime())
                        .arrivedBackAtPitcherAtTime(LocalDateTime.now(clock))
                        .build();

                int packageId = requestPackage.getId();

                measurements.merge(packageId, measurement, mergeMeasurements);

                System.out.println(measurements.get(packageId));
//
// (Measurement oldM, Measurement newM) -> {
//                    return oldM.
//                });

//                logs.stream().filter(l -> l.startsWith(messageId + "_")).findFirst()
//                        .ifPresent(l -> {
//
//                            System.out.println("            -----------             ");
//                            System.out.println("sent at:            " + l.split("_")[1]);
//                            System.out.println("arrived at catcher: " + arrivedAtCatcher);
//                            System.out.println("back to pitcher at: " + receivedAt);
//                            System.out.println("            -----------             ");
//                        });


//                System.out.println("response from catcher: " + line);
            } catch (IOException ex) {
                System.err.println("Failed to load line");
            }


        }
    }

    private static BinaryOperator<Measurement> mergeMeasurements = (sent, received) ->
            Measurement.builder()
                .sentFromPitcherAt(sent.getSentFromPitcherAt())
                .arrivedAtCatcherTime(received.getArrivedAtCatcherTime())
                .arrivedBackAtPitcherAtTime(received.getArrivedBackAtPitcherAtTime()).build();
}
