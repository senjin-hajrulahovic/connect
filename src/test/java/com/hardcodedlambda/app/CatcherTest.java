package com.hardcodedlambda.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SocketNetworkIO.class)
public class CatcherTest {

    @Mock
    SocketNetworkIO socketNetworkIO;

    @Test
    public void test() throws Exception {

        mockStatic(SocketNetworkIO.class);
        when(SocketNetworkIO.instance(null, 0, SocketNetworkIO.Type.SERVER)).thenReturn(socketNetworkIO);
    }

}