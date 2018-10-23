package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.common.Measurement;
import com.hardcodedlambda.app.io.NetworkIO;
import com.hardcodedlambda.app.io.SocketNetworkIO;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class Pitcher {

    private static final int MILLISECONDS_IN_A_SECOND = 1000;

    private static final Map<Integer, Measurement> measurements = new ConcurrentHashMap<>();

    private final int messagesPerSecond;
    // TODO refactor
    private NetworkIO networkIO;

    private PackageEmitter packageEmitter;
    private PitcherResponseListener pitcherResponseListener;
    private Reporter reporter;

    public static Pitcher instance(PitcherConfig config) {

        SocketNetworkIO socketNetworkIO = SocketNetworkIO.instance(config.getHost(), config.getPort(), SocketNetworkIO.Type.CLIENT);
        return new Pitcher(socketNetworkIO, config.getMessagesPerSecond(), config.getMessageSize());
    }

    private Pitcher(NetworkIO socketNetworkIO, int messagesPerSecond, int messageSize) {

        this.networkIO = socketNetworkIO;
        this.messagesPerSecond = messagesPerSecond;

        // TODO pass clock to constructor
        this.packageEmitter = new PackageEmitter(measurements, networkIO, messageSize, Clock.systemDefaultZone());
        this.pitcherResponseListener = new PitcherResponseListener(measurements, networkIO, Clock.systemDefaultZone());
        this.reporter = new Reporter(measurements, Clock.systemDefaultZone());
    }

    public void start() {

        // TODO start at second start rather than somewhere in the middle
        LocalDateTime firstRunTime = LocalDateTime.now().plusSeconds(1).truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime zdt = firstRunTime.atZone(ZoneId.systemDefault());
        Date firstRun = Date.from(zdt.toInstant());


        new Timer().schedule(packageEmitter, firstRun,MILLISECONDS_IN_A_SECOND / messagesPerSecond);

        new Thread(pitcherResponseListener).start();

        new Timer().schedule(reporter, firstRun, MILLISECONDS_IN_A_SECOND);
    }
}
