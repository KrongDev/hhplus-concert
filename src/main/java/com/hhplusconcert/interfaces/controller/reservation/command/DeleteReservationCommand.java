package com.hhplusconcert.interfaces.controller.reservation.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteReservationCommand {
    //
    private String reservationId;

    public void validation() {

    }
}
