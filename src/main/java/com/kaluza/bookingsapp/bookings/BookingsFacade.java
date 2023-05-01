package com.kaluza.bookingsapp.bookings;

import com.kaluza.bookingsapp.bookings.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/*
 * BookingFacade is the only public class in the package and acts as a facade to all the business logic of the application.
 * No other class should be accessed from outside this package.
 */
@AllArgsConstructor
@Slf4j
public class BookingsFacade {
    private BookingsRepository bookingsRepository;
    private BookingRequestValidator bookingRequestValidator;
    private BookingsRebooker bookingsRebooker;

    public Optional<BookingDto> get(String bookingId) {
        return bookingsRepository
                .findById(bookingId)
                .map(BookingConverter::dto);
    }

    public BookingCreatedResponseDto create(BookingCreateRequestDto bookingRequestDto, BookingType type) throws InvalidBookingException {
        //guarding statements
        Collection<BookingValidationResponse> validationResponse = bookingRequestValidator.validate(bookingRequestDto);
        if (!validationResponse.isEmpty()) {
            log.info("validation issues: {}", validationResponse);
            throw new InvalidBookingException(validationResponse);
        }

        boolean overlappingBooking = bookingsRepository.isOverlapping(bookingRequestDto.getPropertyAddress(),
                bookingRequestDto.getBookedFrom(),
                bookingRequestDto.getBookedTo());

        if (overlappingBooking) {
            throw new InvalidBookingException(List.of(new BookingValidationResponse(BookingValidationStatus.OVERLAPPING, "A booking within given date range already exists.")));
        }

        Booking booking = createBooking(bookingRequestDto, type);
        String bookingId = bookingsRepository.save(booking);
        return new BookingCreatedResponseDto(bookingId);
    }

    public boolean cancel(String bookingId) {
        return bookingsRepository.delete(bookingId);
    }

    public Optional<BookingRebookedResponseDto> rebook(BookingRebookRequestDto rebookRequestDto) {
        Optional<BookingRebookedResponseDto> response = bookingsRepository.findById(rebookRequestDto.getBookingId())
                .map(booking -> bookingsRebooker.rebook(booking, rebookRequestDto))
                .map(bookingsRepository::save)
                .map(BookingRebookedResponseDto::new);

        return response;
    }

    public Collection<BookingDto> listBookings() {
        return bookingsRepository.list()
                .stream()
                .filter(booking -> booking.getType() == Booking.BookingType.BOOKING)
                .map(BookingConverter::dto)
                .toList();
    }

    private Booking createBooking(BookingCreateRequestDto bookingRequestDto, BookingType type) {
        Booking booking = Booking.builder()
                .id(createBookingId())
                .bookedFrom(bookingRequestDto.getBookedFrom())
                .bookedTo(bookingRequestDto.getBookedTo())
                .type(type == BookingType.BOOKING ? Booking.BookingType.BOOKING : Booking.BookingType.BLOCK)
                .propertyAddress(BookingPropertyAddress.builder()
                        .address(bookingRequestDto.getPropertyAddress())
                        .build())
                .build();
        return booking;
    }

    private String createBookingId() {
        return UUID.randomUUID().toString();
    }
}
