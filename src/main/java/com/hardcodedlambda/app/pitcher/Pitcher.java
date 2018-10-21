package com.hardcodedlambda.app.pitcher;

import com.hardcodedlambda.app.io.NetworkIO;
import com.hardcodedlambda.app.io.SocketNetworkIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Pitcher {

    private static final int MILLISECONDS_IN_A_SECOND = 1000;

    private final List<String> logs = new ArrayList<>();
    private final int messagesPerSecond;
    private NetworkIO networkIO;

    public static Pitcher instance(PitcherConfig config) throws IOException {

        // TODO make pitcher and catcher conf extends same interface with host and port
        SocketNetworkIO socketNetworkIO = SocketNetworkIO.instance(config.getHost(), config.getPort(), SocketNetworkIO.Type.CLIENT);
        return new Pitcher(socketNetworkIO, config.getMessagesPerSecond());
    }

    private Pitcher(SocketNetworkIO socketNetworkIO, int messagesPerSecond) {

        this.networkIO = socketNetworkIO;
        this.messagesPerSecond = messagesPerSecond;
    }

    public void pitch() throws Exception {

        Timer timer = new Timer();
        timer.schedule(new LogProducer(logs, networkIO), 0, MILLISECONDS_IN_A_SECOND / messagesPerSecond);

        while (true) {
            String line = networkIO.readLine();
            System.out.println("READ: Response From Catcher: " + line);
        }
    }
}
