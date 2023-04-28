package com.kaluza.bookingsapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kaluza.bookingsapp.bookings.BookingsFacade;
import com.kaluza.bookingsapp.bookings.dto.BookingCreateRequestDto;
import com.kaluza.bookingsapp.bookings.dto.BookingCreatedResponseDto;
import com.kaluza.bookingsapp.bookings.dto.BookingType;
import com.kaluza.bookingsapp.bookings.infrastructure.web.BookingsController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookingsController.class)
class BookingsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingsFacade bookingsFacade;

    /*
     * Having more time I would write tests to cover all the BookingsController endpoints.
     */
    @Test
    public void testCreateBooking() throws Exception {

        ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.registerModule(new JavaTimeModule());
        //given
        BookingCreateRequestDto booking = BookingCreateRequestDto.builder()
                .bookedFrom(LocalDate.now())
                .bookedTo(LocalDate.now().plusDays(7))
                .propertyAddress("Poland")
                .build();

        BookingCreatedResponseDto bookingCreatedResponseDto = new BookingCreatedResponseDto("1");
        //when
        when(bookingsFacade.create(booking, BookingType.BOOKING))
                .thenReturn(bookingCreatedResponseDto);

        //then
        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObjectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated());
    }

}
