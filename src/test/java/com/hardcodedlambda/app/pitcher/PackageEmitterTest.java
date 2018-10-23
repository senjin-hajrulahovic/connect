package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.io.NetworkIO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.lang.reflect.Field;
import java.time.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(NetworkIO.class)
public class PackageEmitterTest {

    private static final int MESSAGE_SIZE = 150;

    @Mock
    NetworkIO networkIO;

    @Test
    @SuppressWarnings("unchecked")
    public void testInstantiation() throws NoSuchFieldException, IllegalAccessException {
        PackageEmitter testPackageEmitter = new PackageEmitter(new ConcurrentHashMap<>(), networkIO, MESSAGE_SIZE, Clock.systemDefaultZone());

        Field measurementsField = PackageEmitter.class.getDeclaredField("measurements");
        measurementsField.setAccessible(true);
        Map<Integer, Measurement> measurements = (Map<Integer, Measurement>)measurementsField.get(testPackageEmitter);

        Field messageIdField = PackageEmitter.class.getDeclaredField("nextAvailablePackageId");
        messageIdField.setAccessible(true);
        AtomicInteger messageId = (AtomicInteger)messageIdField.get(testPackageEmitter);

        assertTrue(measurements.isEmpty());
        assertEquals(messageId.intValue(), 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStateAfterRunIsRanOnce() throws NoSuchFieldException, IllegalAccessException {

        String nowString = "2018-10-21T16:51:21.223";
        LocalDateTime fixedNow = LocalDateTime.parse(nowString);

        Clock fixedClock = Clock.fixed(fixedNow.toInstant(OffsetDateTime.now().getOffset()), ZoneOffset.systemDefault());

        PackageEmitter testPackageEmitter = new PackageEmitter(new ConcurrentHashMap<>(), networkIO, MESSAGE_SIZE, fixedClock);
        testPackageEmitter.run();

        Field measurementsField = PackageEmitter.class.getDeclaredField("measurements");
        measurementsField.setAccessible(true);
        Map<Integer, Measurement> measurements = (Map<Integer, Measurement>)measurementsField.get(testPackageEmitter);

        Field nextAvailablePackageIdField = PackageEmitter.class.getDeclaredField("nextAvailablePackageId");
        nextAvailablePackageIdField.setAccessible(true);
        AtomicInteger nextAvailablePackageId = (AtomicInteger)nextAvailablePackageIdField.get(testPackageEmitter);

        assertEquals(measurements.size(), 1);

        assertTrue(measurements.containsKey(0));
        assertEquals(measurements.get(0).getSentFromPitcherAt(), fixedNow);
        assertEquals(nextAvailablePackageId.intValue(), 1);

        verify(networkIO, times(1)).writeLine(anyString());
    }

}