package com.kaluza.bookingsapp.bookings.dto;

import java.time.LocalDate;

public record BookingDto (
    String id,
    LocalDate bookedFrom,
    LocalDate bookedTo,
//    String bookingType,
    String propertyAddress
) { }
