package com.kaluza.bookingsapp.bookings;

import com.kaluza.bookingsapp.bookings.dto.BookingRebookRequestDto;

class BookingsRebooker {
    public Booking rebook(Booking booking, BookingRebookRequestDto rebookRequestDto) {
        return Booking.builder()
                .id(booking.getId())
                .propertyAddress(booking.getPropertyAddress())
                .bookedFrom(rebookRequestDto.bookedFrom())
                .bookedTo(rebookRequestDto.bookedTo())
                .build();
    }
}
