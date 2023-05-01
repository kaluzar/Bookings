import './App.css'
import BookingForm from "./components/BookingForm.tsx";
import BookingTable from "./components/BookingTable.tsx";
import {useEffect, useState} from "react";

function App() {
    const [bookings, setBookings] = useState([]);
    const [booking, setBooking] = useState({id: '', bookedFrom: '', bookedTo: '', propertyAddress: ''});
    const updateBookings = () => {
        fetch('http://localhost:8080/bookings')
            .then(response => response.json())
            .then(data => setBookings(data));
    };
    const rebookBooking = (bookingData) => {
        console.log(bookingData);
        setBooking(bookingData);
    };
    const cancelBooking = (bookingId) => {
        fetch(`http://localhost:8080/bookings/${bookingId}`, {method: 'DELETE'})
            .then(() => updateBookings());
    };

    const handleAddBooking = () => {
        updateBookings();
    };

    const bookingChanged = (bookingData) => {
        setBooking(bookingData);
    }
    useEffect(() => {
        updateBookings();
    }, []);

    return (
        <>
            <BookingForm bookingData={booking} onAddBooking={handleAddBooking} onBookingChanged={bookingChanged}/>
            <hr />
            <BookingTable bookings={bookings} onCancelBooking={cancelBooking} onRebookBooking={rebookBooking}/>
        </>
    )
}

export default App
