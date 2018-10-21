package com.hardcodedlambda.app.pitcher;

public class PitcherConfig {

    private String host;
    private int port;
    private int messagesPerSecond;

    private PitcherConfig() {

    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getMessagesPerSecond() {
        return messagesPerSecond;
    }

    public static class Builder {
        private PitcherConfig config = new PitcherConfig();

        public Builder host(String host) {
            this.config.host = host;
            return this;
        }

        public Builder port(int port) {
            this.config.port = port;
            return this;
        }

        public Builder messagesPerSecond(int messagesPerSecond) {
            this.config.messagesPerSecond = messagesPerSecond;
            return this;
        }

        public PitcherConfig build() {
            return this.config;
        }

    }
}
