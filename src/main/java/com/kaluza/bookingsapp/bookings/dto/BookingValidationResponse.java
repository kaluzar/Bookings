package com.kaluza.bookingsapp.bookings.dto;

public record BookingValidationResponse (
        BookingValidationStatus status,
        String message
) { }
