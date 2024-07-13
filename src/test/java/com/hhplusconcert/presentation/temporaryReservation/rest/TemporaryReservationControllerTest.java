package com.hhplusconcert.presentation.temporaryReservation.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplusconcert.presentation.temporaryReservation.command.CreateTemporaryReservationCommand;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TemporaryReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String concertId = "test_concertId";
    private final String seriesId = "test_seriesId";
    private final String userId = "test_user";
    private final String title = "test_title";
    private final String basicUrl = "/temporary-reservation";
    private final ObjectMapper ob = new ObjectMapper();

    @Test
    @Description("임시예약하려는 정보가 존재하지 않을 때")
    void createTemporaryReservation() throws Exception {
        //GIVEN
        CreateTemporaryReservationCommand command = new CreateTemporaryReservationCommand(
                userId,
                concertId,
                seriesId,
                0,
                0
        );
        //WHEN-THEN
        mockMvc.perform(
            post(basicUrl)
                .content(ob.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isNotFound());
    }
}
