package com.hardcodedlambda.app.catcher;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CatcherConfig {

    private String bind;
    private int port;
}
