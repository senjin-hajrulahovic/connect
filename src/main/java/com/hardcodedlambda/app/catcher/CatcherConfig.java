package com.hardcodedlambda.app.catcher;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CatcherConfig {

    private String bind;
    private int port;
}
