package com.hhplusconcert.interfaces.controller.concert.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplusconcert.interfaces.controller.concert.command.CreateConcertCommand;
import com.hhplusconcert.interfaces.controller.concert.command.CreateConcertSeriesCommand;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String concertId = "test_concertId";
    private String seriesId = "test_seriesId";
    private final String userId = "test_user";
    private final String title = "test_title";
    private final String basicUrl = "/concert";

    private ObjectMapper ob;

    @BeforeEach
    public void setUp() {
        ob = new ObjectMapper();
    }


    @Test
    @Order(1)
    @DisplayName("콘서트 생성 테스트")
    void createConcert() throws Exception {
        //Given
        CreateConcertCommand command = new CreateConcertCommand(userId, title);

        //When
        MvcResult res = mockMvc.perform(
                post(basicUrl)
                    .content(ob.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();
        concertId = res.getResponse().getContentAsString();

        //THEN
        assertNotEquals("", concertId);
    }

    @Test
    @Order(2)
    @DisplayName("콘서트 날짜 생성 테스트")
    void createSeries() throws Exception {
        //GIVEN
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 1);
        long endTime = calendar.getTimeInMillis();
        Long startAt = now;
        Long endAt = endTime;
        Long reserveStartAt = now;
        Long reserveEndAt = endTime;
        int maxRow = 5;
        int maxSeat = 50;
        CreateConcertSeriesCommand command = new CreateConcertSeriesCommand(
                concertId,
                startAt,
                endAt,
                reserveStartAt,
                reserveEndAt,
                maxRow,
                maxSeat
        );
        //WHEN
        seriesId = mockMvc.perform(
                post(basicUrl + "/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ob.writeValueAsString(command))
        ).andReturn().getResponse().getContentAsString();


        //THEN
        assertNotEquals("", seriesId);
    }

    @Test
    @Order(3)
    @DisplayName("콘서트 리스트 조회")
    void loadConcerts() throws Exception {
         //WHEN-THEN
        mockMvc.perform(get(basicUrl))
                .andDo(print())
                .andExpect(jsonPath("$[0].creator").value(userId))
                .andExpect(jsonPath("$[0].title").value(title))
        ;
    }

    @Test
    @Order(4)
    @DisplayName("날짜 조회시 콘서트가 없을 경우 - 에러 발생")
    void loadConcertSeries() throws Exception {
        //WHEN-THEN
        mockMvc.perform(get(basicUrl + "/series/" + "empty_concertId"))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @Order(5)
    @DisplayName("좌석 조회시 콘서트나 날짜가 없을 경우 - 에러 발생")
    void loadConcertSheets() throws Exception {
        //WHEN-THEN
        mockMvc.perform(get(basicUrl + "/seat/" + "empty_seriesId"))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }
}
