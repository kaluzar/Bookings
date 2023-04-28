package com.kaluza.bookingsapp.bookings;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

interface BookingsRepository {
    String save(Booking booking);
    Optional<Booking> findById(String id);
    boolean delete(String id);
    Collection<Booking> list();
    boolean isOverlapping(String propertyAddress, LocalDate bookedFrom, LocalDate bookedTo);
}

class InMemoryBookingRepository implements BookingsRepository {

    private final Map<String, Booking> bookingsStore = new ConcurrentHashMap<>();


    @Override
    public String save(Booking booking) {

        bookingsStore.put(booking.getId(), booking);
        return booking.getId();
    }

    @Override
    public Optional<Booking> findById(String id) {
        return Optional.ofNullable(bookingsStore.get(id));
    }

    @Override
    public boolean delete(String id) {
        return bookingsStore.remove(id) != null;
    }

    @Override
    public Collection<Booking> list() {
        return bookingsStore.values();
    }

    @Override
    public boolean isOverlapping(String propertyAddress, LocalDate bookedFrom, LocalDate bookedTo) {
        // if this was a SQL database there would be some logic utilising the database engine to look for
        // the booking in optimal way, e.g.
        // SELECT * FROM Booking where propertyAddress = :propertyAddress AND bookedFrom > :bookedTo AND bookedTo < :bookedFrom

        //this method ignores the type of booking as booking can't overlap with block, too.

        return bookingsStore.values()
                .stream()
                .filter(booking -> booking.getPropertyAddress().getAddress().equals(propertyAddress))
                .anyMatch(booking -> booking.getBookedFrom().isBefore(bookedTo) && booking.getBookedTo().isAfter(bookedFrom));
    }
}
