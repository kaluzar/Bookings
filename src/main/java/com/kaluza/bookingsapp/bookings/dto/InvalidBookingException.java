package com.kaluza.bookingsapp.bookings.dto;

import java.util.Collection;

public class InvalidBookingException extends Exception {

    private final Collection<BookingValidationResponse> validationResponse;


    public InvalidBookingException(Collection<BookingValidationResponse> validationResponse) {
        this.validationResponse = validationResponse;
    }

    public Collection<BookingValidationResponse> getValidationResponse() {
        return validationResponse;
    }
}
