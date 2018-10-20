package com.hardcodedlambda.app;

import org.apache.commons.cli.*;

public class App {

    public static void main(String[] args) throws Exception {

        final Options options = new Options();

//        OptionGroup mode = new OptionGroup();
//        mode.setRequired(true);
//
//        mode.addOption(Option.builder().argName("c").longOpt("Catcher Mode").optionalArg(true).hasArg(false).build());
//        mode.addOption(Option.builder().argName("p").longOpt("Pitcher Mode").optionalArg(true).hasArg(false).build());
//
//        options.addOptionGroup(mode);

        options.addOption("c", false, "catcher");
        options.addOption("p", false, "pitcher");

        final CommandLineParser cliParser = new DefaultParser();
        final CommandLine cmd = cliParser.parse(options, args);

        if (cmd.hasOption("c")) {
            CatcherConfig catcherConfig = new CatcherConfig();
            catcherConfig.host = "localhost";
            catcherConfig.port = 9092;

           Catcher.instance(catcherConfig).listen();

        } else if (cmd.hasOption("p")) {
            PitcherConfig pitcherConfig = new PitcherConfig();
            pitcherConfig.host = "localhost";
            pitcherConfig.port = 9092;

            Pitcher.instance(pitcherConfig).pitch();
        }
    }
}
