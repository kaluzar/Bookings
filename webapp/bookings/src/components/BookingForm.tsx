import React, {useRef, useState} from 'react';

function BookingForm({ bookingData, onAddBooking, onBookingChanged }) {
    const formRef = useRef(null);

    const buttonLabel = !!bookingData.id ? "Rebook" : "Book";

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        onBookingChanged({ ...bookingData, [name]: value });
    };

    const resetForm = () => {
        formRef.current.reset();
        onBookingChanged({id: '', bookedFrom: '', bookedTo: '', propertyAddress: ''})
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!!bookingData.id) {
            updateBooking(bookingData);
        } else {
            addBooking(bookingData);
        }
    }

    async function processBookingResponse(response: Response) {
        console.log(response.status);
        if (response.status === 400) {
            console.log(await response.json());
            alert(`Booking did not pass validation`);
        } else {
            onAddBooking();
            resetForm();
        }
    }

    const updateBooking = async (data) => {
        const bookingId = data.id;
        const response = await fetch(`http://localhost:8080/bookings/${bookingId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        await processBookingResponse(response);
    }

    const addBooking = async (data) => {
        const response = await fetch('http://localhost:8080/bookings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        await processBookingResponse(response);
    }

    return (
        <form onSubmit={handleSubmit} onReset={resetForm} ref={formRef}>
            <h1>Create a booking</h1>
            <div>
                <label htmlFor="bookedFrom">Booking start date:</label>
                <input type="date" id="bookedFrom" name="bookedFrom" value={bookingData.bookedFrom} onChange={handleInputChange} />
            </div>
            <div>
                <label htmlFor="bookedTo">Booking end date:</label>
                <input type="date" id="bookedTo" name="bookedTo" value={bookingData.bookedTo} onChange={handleInputChange} />
            </div>
            <div>
                <label htmlFor="propertyAddress">Property address:</label>
                <input type="text" id="propertyAddress" name="propertyAddress" value={bookingData.propertyAddress} disabled={!!bookingData.id} onChange={handleInputChange} />
            </div>
            <button type="submit">{buttonLabel}</button>
            <button type="reset">Cancel</button>
        </form>
    );
}

export default BookingForm;