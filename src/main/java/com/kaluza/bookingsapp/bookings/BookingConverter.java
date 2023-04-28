package com.kaluza.bookingsapp.bookings;

import com.kaluza.bookingsapp.bookings.dto.BookingDto;

class BookingConverter {
    static BookingDto dto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getBookedFrom(),
                booking.getBookedTo(),
                booking.getPropertyAddress().getAddress())
                ;
    }
}
