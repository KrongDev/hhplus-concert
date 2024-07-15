package com.hhplusconcert.domain.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // CONCERT_TOKEN
    TOKEN_EXPIRED(401, "Token is expired"),
    TOKEN_NOT_FOUND(404, "Token not found"),
    // WAITING_QUEUE
    WAITING_QUEUE_NOT_FOUND(404, "Queue not found"),
    // CONCERT
    CONCERT_NOT_FOUND(404, "Concert Not Found"),
    // CONCERT_SERIES
    BOOKING_NOT_AVAILABLE(403, "Booking Not Available"),
    CONCERT_SERIES_NOT_FOUND(404, "Concert Series Not Found"),
    // CONCERT_SEAT
    CONCERT_SEAT_NOT_FOUND(404, "Concert Seat Not Found"),
    SEAT_ALREADY_RESERVED(409, "Seat Already Reserved"),
    // PAYMENT
    PAYMENT_REQUIRED(402, "Payment Required"),
    PAYMENT_NOT_FOUND(404, "Payment Not Found"),
    // POINT
    INVALID_POINT(400, "Invalid Point"),
    INSUFFICIENT_POINT(402, "Insufficient Point"),
    POINT_NOT_FOUND(404, "Point Not Found"),
    // TEMPORARY_RESERVATION
    TEMPORARY_RESERVATION_NOT_FOUND(404, "Temporary Reservation Not Found"),
    TEMPORARY_RESERVATION_ALREADY_PURCHASED(409, "Temporary Reservation Already Purchased"),
    PAYMENT_NOT_ALLOWED_FOR_TEMPORARY_RESERVATION(422, "Payment Not Allowed For Temporary Reservation"),
    // RESERVATION
    RESERVATION_NOT_FOUND(404, "Reservation Not Found"),
    ;
    private final int value;
    private final String reasonPhrase;
}
