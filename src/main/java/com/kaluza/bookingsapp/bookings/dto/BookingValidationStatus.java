package com.kaluza.bookingsapp.bookings.dto;

public enum BookingValidationStatus {
    DATE_FROM_MISSING,
    DATE_TO_MISSING,
    DATES_INVALID,
    MISSING_ADDRESS,
    OVERLAPPING
}
