package com.kaluza.bookingsapp.bookings.infrastructure.web;

import com.kaluza.bookingsapp.bookings.BookingsFacade;
import com.kaluza.bookingsapp.bookings.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@AllArgsConstructor
@RestController
@Slf4j
public class BookingsController {
    private static final String BOOKINGS = "/bookings";
    private static final String BLOCKS = "/blocks";
    BookingsFacade bookingsFacade;

    @GetMapping(path = BOOKINGS)
    public Collection<BookingDto> bookings() {
        return bookingsFacade.listBookings();
    }

    @PostMapping(path = BOOKINGS)
    public ResponseEntity<?> book(@RequestBody BookingCreateRequestDto bookingRequest) {
        log.info("Making a new booking: {}", bookingRequest);
        try {
            BookingCreatedResponseDto bookingCreatedResponseDto = bookingsFacade.create(bookingRequest, BookingType.BOOKING);
            return ResponseEntity.created(new URI(BOOKINGS + "/" + bookingCreatedResponseDto.id())).build();
        } catch (InvalidBookingException e) {
            return ResponseEntity.badRequest().body(e.getValidationResponse());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = BOOKINGS + "/{bookingId}")
    public BookingDto get(@PathVariable String bookingId){
        log.info("Getting a booking: {}", bookingId);
        return bookingsFacade.get(bookingId).orElseThrow(BookingNotFoundException::new);
    }

    @PutMapping(path = BOOKINGS + "/{bookingId}")
    public BookingRebookedResponseDto rebook(@PathVariable String bookingId, BookingRebookRequestDto rebookRequestDto){
        log.info("Rebook a booking: {}", rebookRequestDto);
        return bookingsFacade.rebook(rebookRequestDto).orElseThrow(BookingNotFoundException::new);
    }

    @DeleteMapping(path = BOOKINGS + "/{bookingId}")
    public ResponseEntity<?> cancel(@PathVariable String bookingId){
        log.info("Cancelling booking: {}", bookingId);
        boolean canceled = bookingsFacade.cancel(bookingId);

        return canceled
                ? ResponseEntity.ok(bookingId)
                : ResponseEntity.notFound().build();
    }

    /*
        A different endpoint for block has been created on purpose. Blocks seems like a different domain entity from
        the user point of view. We should keep the principle of ubiquitous language and distinguish booking from block
        (probably somewhere along the way it would evolve into two totally separate entities. At this point we will create
        separate endpoints but utilize the commonality of them.
     */
    @PostMapping(path = BLOCKS)
    public ResponseEntity<?> block(@RequestBody BookingCreateRequestDto blockRequest) {
        log.info("Making a new block: {}", blockRequest);
        try {

            BookingCreatedResponseDto bookingCreatedResponseDto = bookingsFacade.create(blockRequest, BookingType.BLOCK);
            return ResponseEntity.created(new URI(BOOKINGS + "/" + bookingCreatedResponseDto.id())).build();
        } catch (InvalidBookingException e) {
            return ResponseEntity.badRequest().body(e.getValidationResponse());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping(path = BLOCKS + "/{blockId}")
    public ResponseEntity<?> delete(@PathVariable String blockId){
        //for simplicity for now it can reuse the exact same logic as cancel booking.
        return cancel(blockId);
    }

}
