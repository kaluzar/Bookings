package com.kaluza.bookingsapp.bookings.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookingNotFoundException extends RuntimeException {
}
