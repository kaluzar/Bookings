package com.kaluza.bookingsapp.bookings.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@RequiredArgsConstructor
public class BookingRebookRequestDto {
    private final String bookingId;
    private final LocalDate bookedFrom;
    private final LocalDate bookedTo;

}
