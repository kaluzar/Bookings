package com.kaluza.bookingsapp.bookings;

import com.kaluza.bookingsapp.bookings.dto.BookingCreateRequestDto;
import com.kaluza.bookingsapp.bookings.dto.BookingType;
import com.kaluza.bookingsapp.bookings.dto.InvalidBookingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class BookingsFacadeTest {

    BookingsRepository bookingsRepository = new InMemoryBookingRepository();
    BookingsFacade bookingsFacade;
    /*
     * Having more time I would write tests to cover all scenarios on the BookingsFacade.
     */

    @BeforeEach
    void setup() {
        bookingsFacade = new BookingsFacade(bookingsRepository, new BookingRequestValidator(), new BookingsRebooker());
    }
    @Test
    void create_fails_when_invalid() {
        //given
            BookingCreateRequestDto booking = BookingCreateRequestDto.builder().build();
        //then
            assertThrows(InvalidBookingException.class, () ->
                            bookingsFacade.create(booking, BookingType.BOOKING)
            );
    }

    @Test
    void create_fails_when_bookings_overlap() throws InvalidBookingException {
        //given
        BookingCreateRequestDto booking1 = BookingCreateRequestDto.builder()
                .bookedFrom(LocalDate.now())
                .bookedTo(LocalDate.now().plusDays(7))
                .propertyAddress("Poland")
                .build();

        BookingCreateRequestDto booking2 = BookingCreateRequestDto.builder()
                .bookedFrom(LocalDate.now().plusDays(5))
                .bookedTo(LocalDate.now().plusDays(10))
                .propertyAddress("Poland")
                .build();

        //when
        bookingsFacade.create(booking1, BookingType.BOOKING);
        //then
        assertThrows(InvalidBookingException.class, () ->
                bookingsFacade.create(booking2, BookingType.BOOKING)
        );
    }

    @Test
    void create_fails_when_booking_overlaps_with_block() throws InvalidBookingException {
        //given
        BookingCreateRequestDto block = BookingCreateRequestDto.builder()
                .bookedFrom(LocalDate.now())
                .bookedTo(LocalDate.now().plusDays(7))
                .propertyAddress("Poland")
                .build();

        BookingCreateRequestDto booking2 = BookingCreateRequestDto.builder()
                .bookedFrom(LocalDate.now().plusDays(5))
                .bookedTo(LocalDate.now().plusDays(10))
                .propertyAddress("Poland")
                .build();

        //when
        bookingsFacade.create(block, BookingType.BLOCK);
        //then
        assertThrows(InvalidBookingException.class, () ->
                bookingsFacade.create(booking2, BookingType.BOOKING)
        );
    }

    @Test
    void create_two_not_overlapping_bookings_should_not_fail() throws InvalidBookingException {
        //given
        BookingCreateRequestDto block = BookingCreateRequestDto.builder()
                .bookedFrom(LocalDate.now())
                .bookedTo(LocalDate.now().plusDays(7))
                .propertyAddress("Poland")
                .build();

        BookingCreateRequestDto booking2 = BookingCreateRequestDto.builder()
                .bookedFrom(LocalDate.now())
                .bookedTo(LocalDate.now().plusDays(7))
                .propertyAddress("UK")
                .build();

        //when
        bookingsFacade.create(block, BookingType.BLOCK);
        // should not throw exception
        bookingsFacade.create(booking2, BookingType.BOOKING);
    }

    @Test
    void create_two_overlapping_bookings_on_different_property_should_not_fail() throws InvalidBookingException {
        //given
        BookingCreateRequestDto block = BookingCreateRequestDto.builder()
                .bookedFrom(LocalDate.now())
                .bookedTo(LocalDate.now().plusDays(7))
                .propertyAddress("Poland")
                .build();

        BookingCreateRequestDto booking2 = BookingCreateRequestDto.builder()
                .bookedFrom(LocalDate.now().plusDays(7))
                .bookedTo(LocalDate.now().plusDays(10))
                .propertyAddress("Poland")
                .build();

        //when
        bookingsFacade.create(block, BookingType.BLOCK);
        // should not throw exception
        bookingsFacade.create(booking2, BookingType.BOOKING);
    }
}