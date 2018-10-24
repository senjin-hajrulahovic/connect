package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.io.SocketNetworkIO;
import com.hardcodedlambda.app.utils.ClockUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.Clock;

import static junit.framework.TestCase.assertTrue;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SocketNetworkIO.class, PackageEmitter.class, PitcherResponseListener.class, Reporter.class})
public class PitcherTest {

    private static final String FIXED_NOW_STRING = "2018-10-21T16:51:21.223";
    private static Clock FIXED_CLOCK = ClockUtils.getFixedClockFromDateString(FIXED_NOW_STRING);

    @Mock
    SocketNetworkIO mockSocketNetworkIO;

    @Test
    public void testInstanceFactory() throws Exception {

        final String host = "localhost";
        final int port = 9092;
        final int messageSize = 10;

        PitcherConfig pitcherConfig = PitcherConfig.builder()
                .host(host)
                .port(port)
                .messageSize(messageSize)
                .build();

        whenNew(SocketNetworkIO.class).withAnyArguments().thenReturn(mockSocketNetworkIO);

        Pitcher testPitcher = Pitcher.instance(pitcherConfig, FIXED_CLOCK);

        verifyNew(SocketNetworkIO.class).withArguments(host, port, SocketNetworkIO.Type.CLIENT);

        assertTrue(testPitcher.measurements.isEmpty());
    }

}