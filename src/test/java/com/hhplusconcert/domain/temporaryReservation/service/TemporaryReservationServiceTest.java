package com.hhplusconcert.domain.temporaryReservation.service;

import com.hhplusconcert.domain.common.exception.model.CustomGlobalException;
import com.hhplusconcert.domain.common.exception.model.vo.ErrorType;
import com.hhplusconcert.domain.temporaryReservation.model.TemporaryReservation;
import com.hhplusconcert.domain.temporaryReservation.repository.TemporaryReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemporaryReservationServiceTest {
    @InjectMocks
    private TemporaryReservationService temporaryReservationService;

    @Mock
    private TemporaryReservationRepository temporaryReservationRepository;

    @Test
    @DisplayName("결제가 이미 된 경우-TEMPORARY_RESERVATION_ALREADY_PURCHASED 에러 발생")
    public void temporaryReservationPaid() {
        //GIVEN
        String temporaryReservationId = "test_temporary_reservation_id";
        when(temporaryReservationRepository.findById(temporaryReservationId)).thenReturn(TemporaryReservation.builder().paid(true).build());
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> temporaryReservationService.payReservation(temporaryReservationId));
        //THEN
        assertEquals(ErrorType.TEMPORARY_RESERVATION_ALREADY_PURCHASED, exception.getErrorType());
    }

    @Test
    @DisplayName("결제가 안되었지만 시간지나 삭제된 경우-PAYMENT_NOT_ALLOWED_FOR_TEMPORARY_RESERVATION 에러 발생")
    public void temporaryReservationDeleted() {
        //GIVEN
        String temporaryReservationId = "test_temporary_reservation_id";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -1);
        Long deleteAt = calendar.getTimeInMillis();
        when(temporaryReservationRepository.findById(temporaryReservationId)).thenReturn(TemporaryReservation.builder().deleteAt(deleteAt).build());
        //WHEN
        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> temporaryReservationService.payReservation(temporaryReservationId));
        //THEN
        assertEquals(ErrorType.PAYMENT_NOT_ALLOWED_FOR_TEMPORARY_RESERVATION, exception.getErrorType());
    }
}
