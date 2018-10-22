package com.hardcodedlambda.app.common;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class Measurement {

    private LocalDateTime sentFromPitcherAt;
    private LocalDateTime arrivedAtCatcherTime;
    private LocalDateTime arrivedBackAtPitcherAtTime;

}
