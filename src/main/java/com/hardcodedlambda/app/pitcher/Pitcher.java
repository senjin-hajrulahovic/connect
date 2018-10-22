package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.TestPackage;
import com.hardcodedlambda.app.io.NetworkIO;
import com.hardcodedlambda.app.io.SocketNetworkIO;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static java.util.Collections.synchronizedList;

public class Pitcher {

    private static final int MILLISECONDS_IN_A_SECOND = 1000;

    private final List<TestPackage> sentPackages = synchronizedList(new ArrayList<>());
    private final int messagesPerSecond;
    private NetworkIO networkIO;

    private LogProducer logProducer;
    private ResponseListener responseListener;
    private Reporter reporter;

    public static Pitcher instance(PitcherConfig config) throws IOException {

        // TODO make pitcher and catcher conf extends same interface with host and port
        SocketNetworkIO socketNetworkIO = SocketNetworkIO.instance(config.getHost(), config.getPort(), SocketNetworkIO.Type.CLIENT);
        return new Pitcher(socketNetworkIO, config.getMessagesPerSecond(), config.getMessageSize());
    }

    private Pitcher(NetworkIO socketNetworkIO, int messagesPerSecond, int messageSize) {

        this.networkIO = socketNetworkIO;
        this.messagesPerSecond = messagesPerSecond;

        this.logProducer = new LogProducer(sentPackages, networkIO, messageSize, Clock.systemDefaultZone());
        this.responseListener = new ResponseListener(sentPackages, networkIO, Clock.systemDefaultZone());
        this.reporter = new Reporter(sentPackages, Clock.systemDefaultZone());
    }

    public void start() {

        new Timer().schedule(logProducer, 0
                , MILLISECONDS_IN_A_SECOND / messagesPerSecond
        );

        new Thread(responseListener).start();

        new Timer().schedule(reporter, 0, MILLISECONDS_IN_A_SECOND);
    }
}
