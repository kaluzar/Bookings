package com.kaluza.bookingsapp.bookings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
class Booking {
    private final String id;
    private final LocalDate bookedFrom;
    private final LocalDate bookedTo;
    private final BookingPropertyAddress propertyAddress;
    private final BookingType type;

    enum BookingType {
        BOOKING,
        BLOCK
    }
}
