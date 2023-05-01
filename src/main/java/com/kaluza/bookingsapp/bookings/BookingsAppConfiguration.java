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
        // Booking bean is defined here instead fo @Service annotation to limit dependencies on frameworks on business logic
        return new BookingsFacade(bookingsRepository(), bookingRequestValidator(), bookingsRebooker());
    }
}
