package com.hhplusconcert.interfaces.controller.point.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplusconcert.interfaces.controller.point.dto.ChargePointRequest;
import com.hhplusconcert.interfaces.controller.point.dto.UsePointRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUrl = "/point";
    private final String userId = "test_userId";
    private final int amount = 1000;

    @Test
    @Order(1)
    @DisplayName("포인트 충전")
    void chargePoint() throws Exception {
        mockMvc.perform(patch(baseUrl + "/charge")
                .content(objectMapper.writeValueAsString(new ChargePointRequest(userId, amount)))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("포인트 사용")
    void usePoint() throws Exception {
        mockMvc.perform(patch(baseUrl + "/use")
                        .content(objectMapper.writeValueAsString(new UsePointRequest(userId, "test_paymentId", amount)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("포인트 조회")
    void loadPoint() throws Exception {
        mockMvc.perform(get(baseUrl + "/" + userId)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("포인트 작업 내역들 조회")
    void loadPointHistories() throws Exception {
        mockMvc.perform(get(baseUrl + "/history/" + userId)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
