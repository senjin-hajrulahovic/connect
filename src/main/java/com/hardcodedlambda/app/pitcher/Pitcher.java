package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.io.NetworkIO;
import com.hardcodedlambda.app.io.SocketNetworkIO;

import java.time.Clock;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import static com.hardcodedlambda.app.utils.TimeUtils.truncateToNextSecond;

public class Pitcher {

    private static final int MILLISECONDS_IN_A_SECOND = 1000;
    private static final Map<Integer, Measurement> measurements = new ConcurrentHashMap<>();

    private NetworkIO networkIO;
    private Clock clock;

    private final int messagesPerSecond;

    private PackageEmitter packageEmitter;
    private PitcherResponseListener pitcherResponseListener;
    private Reporter reporter;


    public static Pitcher instance(PitcherConfig config, Clock clock) {

        SocketNetworkIO socketNetworkIO = SocketNetworkIO.instance(config.getHost(), config.getPort(), SocketNetworkIO.Type.CLIENT);
        return new Pitcher(socketNetworkIO, clock, config.getMessagesPerSecond(), config.getMessageSize());
    }

    private Pitcher(NetworkIO socketNetworkIO, Clock clock, int messagesPerSecond, int messageSize) {

        this.networkIO = socketNetworkIO;
        this.messagesPerSecond = messagesPerSecond;

        this.packageEmitter = new PackageEmitter(measurements, networkIO, messageSize, clock);
        this.pitcherResponseListener = new PitcherResponseListener(measurements, networkIO, clock);
        this.reporter = new Reporter(measurements, clock);
        this.clock = clock;
    }

    public void start() {

        Date firstRun = truncateToNextSecond(clock);

        new Timer().schedule(packageEmitter, firstRun,MILLISECONDS_IN_A_SECOND / messagesPerSecond);
        new Thread(pitcherResponseListener).start();
        new Timer().schedule(reporter, firstRun, MILLISECONDS_IN_A_SECOND);
    }
}
