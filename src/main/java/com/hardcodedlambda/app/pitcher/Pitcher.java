package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.io.NetworkIO;
import com.hardcodedlambda.app.io.SocketNetworkIO;

import java.io.IOException;
import java.time.Clock;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class Pitcher {

    private static final int MILLISECONDS_IN_A_SECOND = 1000;


    private static final Map<Integer, Measurement> measurements = new ConcurrentHashMap<>();

    private final int messagesPerSecond;
    // TODO refactor
    private NetworkIO networkIO;

    private LogProducer logProducer;
    private ResponseListener responseListener;
    private Reporter reporter;

    public static Pitcher instance(PitcherConfig config) throws IOException {

        SocketNetworkIO socketNetworkIO = SocketNetworkIO.instance(config.getHost(), config.getPort(), SocketNetworkIO.Type.CLIENT);
        return new Pitcher(socketNetworkIO, config.getMessagesPerSecond(), config.getMessageSize());
    }

    private Pitcher(NetworkIO socketNetworkIO, int messagesPerSecond, int messageSize) {

        this.networkIO = socketNetworkIO;
        this.messagesPerSecond = messagesPerSecond;

        // TODO pass clock to constructor
        this.logProducer = new LogProducer(measurements, networkIO, messageSize, Clock.systemDefaultZone());
        this.responseListener = new ResponseListener(measurements, networkIO, Clock.systemDefaultZone());
        this.reporter = new Reporter(measurements, Clock.systemDefaultZone());
    }

    public void start() {

        // TODO start at second start rather than somewhere in the middle
        new Timer().schedule(logProducer, 0
                , MILLISECONDS_IN_A_SECOND / messagesPerSecond
        );

        new Thread(responseListener).start();

        new Timer().schedule(reporter, 0, MILLISECONDS_IN_A_SECOND);
    }
}
