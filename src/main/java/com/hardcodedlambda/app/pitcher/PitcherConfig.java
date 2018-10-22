package com.hardcodedlambda.app.pitcher;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PitcherConfig {

    private String host;
    private int port;
    private int messagesPerSecond;
    private int messageSize;
}
