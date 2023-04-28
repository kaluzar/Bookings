import React, {useRef, useState} from 'react';

function BookingForm({ booking, onAddBooking }) {
    const [id, setId] = useState(booking.id);
    const [bookedFrom, setBookedFrom] = useState(booking.bookedFrom);
    const [bookedTo, setBookedTo] = useState(booking.bookedTo);
    const [propertyAddress, setPropertyAddress] = useState(booking.propertyAddress);
    const formRef = useRef(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const data = { bookedFrom, bookedTo, propertyAddress };

        const resetForm = () => {
            formRef.current.reset();
            setBookedFrom('');
            setBookedTo('');
            setPropertyAddress('');
            setId('');
        }

        const response = await fetch('http://localhost:8080/bookings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        await response;
        console.log(response.status);
        if (response.status === 400) {
            console.log(await response.json());
            alert(`Booking did not pass validation`);
        } else {
            onAddBooking();
            resetForm();
        }
    }

    return (
        <form onSubmit={handleSubmit} ref={formRef}>
            <h1>Create a booking</h1>
            <div>
                <label htmlFor="bookedFrom">Booking start date:</label>
                <input type="date" id="bookedFrom" name="bookedFrom" value={bookedFrom} onChange={(e) => setBookedFrom(e.target.value)} />
            </div>
            <div>
                <label htmlFor="bookedTo">Booking end date:</label>
                <input type="date" id="bookedTo" name="bookedTo" value={bookedTo} onChange={(e) => setBookedTo(e.target.value)} />
            </div>
            <div>
                <label htmlFor="propertyAddress">Property address:</label>
                <input type="text" id="propertyAddress" name="propertyAddress" value={propertyAddress} disabled={!!id} onChange={(e) => setPropertyAddress(e.target.value)} />
            </div>
            <button type="submit">Book</button>
        </form>
    );
}

export default BookingForm;