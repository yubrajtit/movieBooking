package com.example.moviesBookingApplication.Dto;

import com.example.moviesBookingApplication.Entity.BookingStatus;
import com.example.moviesBookingApplication.Entity.Show;
import com.example.moviesBookingApplication.Entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {

    private Integer numberOfSeats;
    private LocalDateTime bookingTime;
    private Double Price;
    private BookingStatus bookingStatus;
    private List<String> seatNumbers;
    private User userId;
    private Show showId;
}
