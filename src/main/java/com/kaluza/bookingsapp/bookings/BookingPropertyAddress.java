package com.kaluza.bookingsapp.bookings;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class BookingPropertyAddress {
    private final String address;
}
