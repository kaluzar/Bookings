function BookingTable({ bookings, onCancelBooking, onRebookBooking }) {

    const handleCancelBooking = (bookingId) => {
        console.log('cancel ' + bookingId);
        onCancelBooking(bookingId);
    };

    const handleRebookBooking = (booking) => {
        onRebookBooking(booking);
    };


    return (
        <div>
            <h1>List of bookings</h1>
            <table>
                <thead>
                <tr>
                    <th>Booking ID</th>
                    <th>Property Address</th>
                    <th>Booking Start Date</th>
                    <th>Booking End Date</th>
                </tr>
                </thead>
                <tbody>
                {bookings.map((booking) => (
                    <tr key={booking.id}>
                        <td>{booking.id}</td>
                        <td>{booking.propertyAddress}</td>
                        <td>{booking.bookedFrom}</td>
                        <td>{booking.bookedTo}</td>
                        <td><button onClick={() => handleCancelBooking(booking.id)}>Cancel</button></td>
                        <td><button onClick={() => handleRebookBooking(booking)}>Rebook</button></td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default BookingTable;