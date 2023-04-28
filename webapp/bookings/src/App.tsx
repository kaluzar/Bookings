import './App.css'
import BookingForm from "./components/BookingForm.tsx";
import BookingTable from "./components/BookingTable.tsx";
import {useEffect, useState} from "react";

function App() {
    const [bookings, setBookings] = useState([]);

    const updateBookings = () => {
        console.log('update bookings');
        fetch('http://localhost:8080/bookings')
            .then(response => response.json())
            .then(data => setBookings(data));
    };
    const rebookBooking = (booking) => {
        console.log(booking);
    };
    const cancelBooking = (bookingId) => {
        fetch(`http://localhost:8080/bookings/${bookingId}`, {method: 'DELETE'})
            .then(() => updateBookings());
    };

    const handleAddBooking = () => {
        //setBookings([...bookings, newBooking]);
        updateBookings();
    };

    useEffect(() => {
        updateBookings();
    }, []);

    return (
    <>
        <BookingForm onAddBooking={handleAddBooking} />
        <hr />
        <BookingTable bookings={bookings} onCancelBooking={cancelBooking} onRebookBooking={rebookBooking}/>
    </>
)
}

export default App
