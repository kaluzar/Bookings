package com.kaluza.bookingsapp.bookings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookingsAppConfiguration {
    BookingsRepository bookingsRepository() {
        return new InMemoryBookingRepository();
    }

    BookingsRebooker bookingsRebooker() {
        return new BookingsRebooker();
    }

    BookingRequestValidator bookingRequestValidator() {
        return new BookingRequestValidator();
    }
    @Bean
    BookingsFacade bookingsFacade() {
        return new BookingsFacade(bookingsRepository(), bookingRequestValidator(), bookingsRebooker());
    }
}
