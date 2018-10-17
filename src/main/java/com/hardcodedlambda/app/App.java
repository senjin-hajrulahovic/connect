package com.hardcodedlambda.app;

import org.apache.commons.cli.*;

public class App {
    public static void main(String[] args) throws ParseException {

        Options options = new Options();
        options.addOption("c", true, "catcher");

        CommandLineParser cliParser = new DefaultParser();
        CommandLine cmd = cliParser.parse(options, args);

        System.out.println("Hello cmd!");
    }
}
