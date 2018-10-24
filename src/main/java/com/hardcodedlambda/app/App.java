package com.hardcodedlambda.app;

import com.hardcodedlambda.app.catcher.Catcher;
import com.hardcodedlambda.app.catcher.CatcherConfig;
import com.hardcodedlambda.app.pitcher.Pitcher;
import com.hardcodedlambda.app.pitcher.PitcherConfig;
import org.apache.commons.cli.*;

import java.time.Clock;

public class App {

    public static void main(String[] args) throws Exception {

        CommandLine cmd = cmd(args);

        if (cmd.hasOption("c")) {

            CatcherConfig catcherConfig = CatcherConfig.builder()
                    .bind(cmd.getOptionValue("bind"))
                    .port(Integer.valueOf(cmd.getOptionValue("port")))
                    .build();

            Catcher.instance(catcherConfig).listen();

        } else if (cmd.hasOption("p")) {

            PitcherConfig pitcherConfig = PitcherConfig.builder()
                    .host(cmd.getOptionValue("hostname"))
                    .port(Integer.valueOf(cmd.getOptionValue("port")))
                    .messagesPerSecond(Integer.valueOf(cmd.getOptionValue("mps")))
                    .messageSize(Integer.valueOf(cmd.getOptionValue("size")))
                    .build();

            Pitcher.instance(pitcherConfig, Clock.systemDefaultZone()).start();
        }
    }

    private static CommandLine cmd(String[] args) throws ParseException {

        final Options options = new Options();

        options.addOption("p", false, "pitcher mode");
        options.addOption("c", false, "catcher mode");

        options.addOption("port", true, "port");
        options.addOption("bind", true, "socket bind ip");

        options.addOption("mps", true, "messages per second");
        options.addOption("size", true, "size of sent message");
        options.addOption("hostname", true, "hostname");

        final CommandLineParser cliParser = new DefaultParser();
        return cliParser.parse(options, args);

    }
}
