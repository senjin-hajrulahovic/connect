package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.NetworkIO;
import com.hardcodedlambda.app.utils.ClockUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PitcherResponseListenerTest {

    private static final String FIXED_NOW_STRING = "2018-10-21T16:51:21.223";
    private static Clock FIXED_CLOCK = ClockUtils.getFixedClockFromDateString(FIXED_NOW_STRING);
    private static LocalDateTime FIXED_NOW = LocalDateTime.now(FIXED_CLOCK);

    @Mock
    private NetworkIO networkIO;

    Map<Integer, Measurement> testMeasurements;

    @Before
    public void setup() {
        testMeasurements = new ConcurrentHashMap<>();
    }

    @Test
    public void testResponseWithOnePackage() {

        int packageId = 0;

        LocalDateTime sentFromPitcher = FIXED_NOW.minusSeconds(20);

        Measurement measurement = Measurement.builder()
                .sentFromPitcherAt(sentFromPitcher)
                .build();

        testMeasurements.put(packageId, measurement);

        LocalDateTime arrivedAtCatcher = FIXED_NOW.minusSeconds(10);

        RequestPackage responseFromCatcher = RequestPackage.builder()
                .id(packageId)
                .time(arrivedAtCatcher)
                .size(50).build();

        when(networkIO.readLine()).thenReturn(responseFromCatcher.toString()).thenReturn(null);

        PitcherResponseListener testPitcherResponseListener = new PitcherResponseListener(testMeasurements, networkIO, FIXED_CLOCK);
        testPitcherResponseListener.run();

        assertEquals(testMeasurements.size(), 1);
        assertEquals(testMeasurements.get(0).getArrivedAtCatcherAt(), arrivedAtCatcher);
        assertEquals(testMeasurements.get(0).getArrivedBackAtPitcherAt(), FIXED_NOW);

        assertTrue(testMeasurements.get(0).completedRoundTrip());
    }

    @Test
    public void testMeasurementMerger() throws NoSuchFieldException, IllegalAccessException {

        Field measurementsMergerField = PitcherResponseListener.class.getDeclaredField("mergeMeasurements");
        measurementsMergerField.setAccessible(true);
        BinaryOperator<Measurement> measurements = (BinaryOperator<Measurement>)measurementsMergerField.get(measurementsMergerField);

        LocalDateTime sentFromPitcher = FIXED_NOW.minusSeconds(20);

        Measurement sent = Measurement.builder()
                .sentFromPitcherAt(sentFromPitcher)
                .build();


        LocalDateTime arrivedAtCatcher = FIXED_NOW.minusSeconds(10);

        Measurement received = Measurement.builder()
                .arrivedAtCatcherAt(arrivedAtCatcher)
                .arrivedBackAtPitcherAt(FIXED_NOW)
                .build();

        Measurement mergeResult = measurements.apply(sent, received);

        assertTrue(mergeResult.completedRoundTrip());
    }

}