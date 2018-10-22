package com.hardcodedlambda.app.pitcher;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class PitcherConfig {

    private String host;
    private int port;
    private int messagesPerSecond;
    private int messageSize;
}
