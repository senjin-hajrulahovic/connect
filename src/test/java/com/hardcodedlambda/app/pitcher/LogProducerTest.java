package com.hardcodedlambda.app.pitcher;

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

        Field stateField = LogProducer.class.getDeclaredField("state");
        stateField.setAccessible(true);
        List<String> state = (List<String>)stateField.get(testLogProducer);

        Field messageIdField = LogProducer.class.getDeclaredField("nextAvailablePackageId");
        messageIdField.setAccessible(true);
        AtomicInteger messageId = (AtomicInteger)messageIdField.get(testLogProducer);

        assertTrue(state.isEmpty());
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

        Field stateField = LogProducer.class.getDeclaredField("state");
        stateField.setAccessible(true);
        List<String> state = (List<String>)stateField.get(testLogProducer);

        Field messageIdField = LogProducer.class.getDeclaredField("nextAvailablePackageId");
        messageIdField.setAccessible(true);
        AtomicInteger messageId = (AtomicInteger)messageIdField.get(testLogProducer);

        assertEquals(state.size(), 1);
        assertTrue(state.stream().anyMatch(s -> s.contains(nowString)));
        assertEquals(state.get(0).length(), MESSAGE_SIZE);
        assertEquals(messageId.intValue(), 1);
    }

}