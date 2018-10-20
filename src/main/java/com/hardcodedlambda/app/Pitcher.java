package com.hardcodedlambda.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Pitcher {

    private final List<String> logs = new ArrayList<>();
    private IO networkIO;

    public static Pitcher instance(PitcherConfig config) throws IOException {

        NetworkIO networkIO = new NetworkIO(config.host, config.port, NetworkIO.Type.CLIENT);
        return new Pitcher(networkIO);
    }

    private Pitcher(NetworkIO networkIO) {
        this.networkIO = networkIO;
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
