package com.kaluza.bookingsapp.bookings.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public record BookingRebookRequestDto (
    String bookingId,
    LocalDate bookedFrom,
    LocalDate bookedTo
) { }
