package com.hardcodedlambda.app.catcher;

public class CatcherConfig {

    private String bind;
    private int port;

    private CatcherConfig() {

    }

    public String getBind() {
        return bind;
    }

    public int getPort() {
        return port;
    }

    public static class Builder {
        private CatcherConfig config = new CatcherConfig();

        public Builder bind(String bind) {
            this.config.bind = bind;
            return this;
        }

        public Builder port(int port) {
            this.config.port = port;
            return this;
        }

        public CatcherConfig build() {
            return this.config;
        }

    }
}
