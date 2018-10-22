package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.TestPackage;
import com.hardcodedlambda.app.io.NetworkIO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class LogProducerTest {

    private static final int MESSAGE_SIZE = 150;

    @Mock
    NetworkIO networkIO;

    @Test
    @SuppressWarnings("unchecked")
    public void testInstantiation() throws NoSuchFieldException, IllegalAccessException {
        LogProducer testLogProducer = new LogProducer(new ArrayList<>(), networkIO, MESSAGE_SIZE, Clock.systemDefaultZone());

        Field stateField = LogProducer.class.getDeclaredField("sentPackages");
        stateField.setAccessible(true);
        List<TestPackage> sentPackages = (List<TestPackage>)stateField.get(testLogProducer);

        Field messageIdField = LogProducer.class.getDeclaredField("nextAvailablePackageId");
        messageIdField.setAccessible(true);
        AtomicInteger messageId = (AtomicInteger)messageIdField.get(testLogProducer);

        assertTrue(sentPackages.isEmpty());
        assertEquals(messageId.intValue(), 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testStateAfterRunIsRanOnce() throws NoSuchFieldException, IllegalAccessException {

        String nowString = "2018-10-21T16:51:21.223";
        LocalDateTime fixedNow = LocalDateTime.parse(nowString);

        Clock fixedClock = Clock.fixed(fixedNow.toInstant(OffsetDateTime.now().getOffset()), ZoneOffset.systemDefault());

        LogProducer testLogProducer = new LogProducer(new ArrayList<>(), networkIO, MESSAGE_SIZE, fixedClock);
        testLogProducer.run();

        Field sentPackagesField = LogProducer.class.getDeclaredField("sentPackages");
        sentPackagesField.setAccessible(true);
        List<TestPackage> sentPackages = (List<TestPackage>)sentPackagesField.get(testLogProducer);

        Field messageIdField = LogProducer.class.getDeclaredField("nextAvailablePackageId");
        messageIdField.setAccessible(true);
        AtomicInteger messageId = (AtomicInteger)messageIdField.get(testLogProducer);

        assertEquals(sentPackages.size(), 1);

        assertEquals(sentPackages.get(0).toString().length(), MESSAGE_SIZE);
        assertEquals(messageId.intValue(), 1);
    }

}