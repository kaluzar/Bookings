package com.kaluza.bookingsapp.bookings;

import com.kaluza.bookingsapp.bookings.dto.BookingCreateRequestDto;
import com.kaluza.bookingsapp.bookings.dto.BookingValidationResponse;
import com.kaluza.bookingsapp.bookings.dto.BookingValidationStatus;

import java.util.Collection;
import java.util.HashSet;

public class BookingRequestValidator {
    public Collection<BookingValidationResponse> validate(BookingCreateRequestDto bookingRequestDto) {
        Collection<BookingValidationResponse> validationResult = new HashSet<>();
        if (bookingRequestDto.getBookedFrom() == null) {
            validationResult.add(new BookingValidationResponse(BookingValidationStatus.DATE_FROM_MISSING, "Booking from date missing"));
        }
        if (bookingRequestDto.getBookedTo() == null) {
            validationResult.add(new BookingValidationResponse(BookingValidationStatus.DATE_TO_MISSING, "Booking to date missing"));
        }
        if (bookingRequestDto.getBookedFrom() != null
            && bookingRequestDto.getBookedTo() != null
            && bookingRequestDto.getBookedFrom().isAfter(bookingRequestDto.getBookedTo())) {
            validationResult.add(new BookingValidationResponse(BookingValidationStatus.DATES_INVALID,
                    String.format("Booking dates missmatched: from %s, to %s",
                            bookingRequestDto.getBookedFrom(),
                            bookingRequestDto.getBookedTo())));
        }
        if (bookingRequestDto.getPropertyAddress() == null
            || bookingRequestDto.getPropertyAddress().trim().length() == 0) {
            validationResult.add(new BookingValidationResponse(BookingValidationStatus.MISSING_ADDRESS, "Booking has no address"));
        }

        return validationResult;
    }
}
