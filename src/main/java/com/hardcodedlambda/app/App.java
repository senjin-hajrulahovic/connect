package com.hardcodedlambda.app;

import com.hardcodedlambda.app.catcher.Catcher;
import com.hardcodedlambda.app.catcher.CatcherConfig;
import com.hardcodedlambda.app.pitcher.Pitcher;
import com.hardcodedlambda.app.pitcher.PitcherConfig;
import org.apache.commons.cli.*;

public class App {

    public static void main(String[] args) throws Exception {

        CommandLine cmd = cmd(args);

        if (cmd.hasOption("c")) {

            CatcherConfig catcherConfig = new CatcherConfig.Builder()
                    .bind(cmd.getOptionValue("bind"))
                    .port(Integer.valueOf(cmd.getOptionValue("port")))
                    .build();

           Catcher.instance(catcherConfig).listen();

        } else if (cmd.hasOption("p")) {

            PitcherConfig pitcherConfig = new PitcherConfig.Builder()
                    .host("localhost")
                    .port(Integer.valueOf(cmd.getOptionValue("port")))
                    // TODO validate mps
                    .messagesPerSecond(Integer.valueOf(cmd.getOptionValue("mps")))
                    .build();

            Pitcher.instance(pitcherConfig).pitch();
        }
    }

    private static final CommandLine cmd(String[] args) throws ParseException {

        final Options options = new Options();

        options.addOption("c", false, "catcher");
        options.addOption("p", false, "pitcher");
        options.addOption("mps", true, "messages per second");
        options.addOption("port", true, "port");

        final CommandLineParser cliParser = new DefaultParser();
        return cliParser.parse(options, args);

    }
}
