package com.hardcodedlambda.app.catcher;

import com.hardcodedlambda.app.common.RequestPackage;
import com.hardcodedlambda.app.io.SocketNetworkIO;
import com.hardcodedlambda.app.utils.ClockUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PackageResponderTest {

    @Mock
    SocketNetworkIO socketNetworkIO;

    private static final String FIXED_NOW_STRING = "2018-10-21T16:51:21.223";
    private static Clock FIXED_CLOCK = ClockUtils.getFixedClockFromDateString(FIXED_NOW_STRING);
    private static LocalDateTime FIXED_NOW = LocalDateTime.now(FIXED_CLOCK);

    @Test
    public void testQueueEmptying() throws Exception {

        ConcurrentLinkedQueue<RequestPackage> receivedPackages = new ConcurrentLinkedQueue<>();

        RequestPackage firstRequestPackage = RequestPackage.builder()
                .id(1)
                .time(FIXED_NOW.minusSeconds(10))
                .size(150)
                .build();

        RequestPackage secondRequestPackage = RequestPackage.builder()
                .id(2)
                .time(FIXED_NOW.minusSeconds(10))
                .size(150)
                .build();

        receivedPackages.add(firstRequestPackage);
        receivedPackages.add(secondRequestPackage);

        PackageResponder testResponder = new PackageResponder(socketNetworkIO, receivedPackages, FIXED_CLOCK);

        testResponder.stopAfter(1000L);
        new Thread(() -> testResponder.run()).run();

        assertTrue(receivedPackages.isEmpty());
    }


}