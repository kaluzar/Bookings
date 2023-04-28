package com.kaluza.bookingsapp.bookings.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
public class BookingCreateRequestDto {
    private final LocalDate bookedFrom;
    private final LocalDate bookedTo;
    private final String propertyAddress;
}
