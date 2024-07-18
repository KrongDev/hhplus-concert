package com.hhplusconcert.interfaces.scheduler;

import com.hhplusconcert.application.temporaryReservation.facade.TemporaryReservationFlowFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemporaryReservationScheduler {
    //
    private final TemporaryReservationFlowFacade temporaryReservationFlowFacade;

    @Scheduled(fixedDelay = 3000)
    public void timeLimitTemporaryReservation () {
        //
        this.temporaryReservationFlowFacade.timeLimitTemporaryReservation();
    }
}
