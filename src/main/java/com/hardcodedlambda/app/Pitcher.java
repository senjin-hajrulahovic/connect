package com.hardcodedlambda.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Pitcher {

    private final List<String> logs = new ArrayList<>();
    private NetworkIO networkIO;

    public static Pitcher instance(PitcherConfig config) throws IOException {

        SocketNetworkIO socketNetworkIO = SocketNetworkIO.instance(config.host, config.port, SocketNetworkIO.Type.CLIENT);
        return new Pitcher(socketNetworkIO);
    }

    private Pitcher(SocketNetworkIO socketNetworkIO) {
        this.networkIO = socketNetworkIO;
    }

    public void pitch() throws Exception {

        Timer timer = new Timer();
        timer.schedule(new LogProducer(logs, networkIO), 0, 1000);

        while (true) {
            String line = networkIO.readLine();
            System.out.println("READ: Response From Catcher: " + line);
        }
    }
}
