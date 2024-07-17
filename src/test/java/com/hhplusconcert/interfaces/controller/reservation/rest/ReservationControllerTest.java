package com.hhplusconcert.interfaces.controller.reservation.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReservationControllerTest {
    //
    @Autowired
    private MockMvc mockMvc;

    private final String reservationId = "test_reservation_id";
    private final String baseUrl = "/reservation";

    @Test
    @DisplayName("예약 티켓이 없을경우 에러 발생")
    void loadReservation() throws Exception {
        mockMvc.perform(get(baseUrl + "/" + reservationId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
