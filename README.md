# Task description
Prepare a simple booking API that enables to create a booking, rebook or cancel a booking. Additionally it should be possible to create and delete a block.

# Prerequisites
1. For simplicity address is just a string.
2. At this stage we do not care for creator of the booking/block so he is not included.
3. Requirements did not specify what should happen when owner wants to submit a block for a property that already has a booking at given time so it returns error.

# Backend Solution
I have decided to implement the solution in a clean architecture approach where all the business
logic is separated from the infrastructure. 
This approach allows to have an independent business logic and provides an easy
way to change the infrastructure or the persistence layer without any changes required within.

# Missing parts
1. Not all tests have been written
2. No jacoco report prepared to show test coverage
3. Missing documentation for BookingController (e.g. Swagger)
4. Using a true persistence layer, not the in-memory one.
5. CI pipeline prepared to build and test the project continuously.
6. Initial tests on live service written in BookingsApplication.http, further methods should be tested
7. Bundle frontend and backend together
8. Frontend tests.
9. Frontend Typescript types.

# Running the application
1. Run the BookingsApplication Spring Boot application from an IDE.
2. run `npm run dev` from webapp/bookings/ directory
   1. Open browser using url provided in the command response, most probably `http://localhost:5173`
