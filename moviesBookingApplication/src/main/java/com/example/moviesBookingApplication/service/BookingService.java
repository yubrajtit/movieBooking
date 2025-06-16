package com.example.moviesBookingApplication.service;

import com.example.moviesBookingApplication.Dto.BookingDTO;
import com.example.moviesBookingApplication.Entity.Booking;
import com.example.moviesBookingApplication.Entity.BookingStatus;
import com.example.moviesBookingApplication.Entity.Show;
import com.example.moviesBookingApplication.Entity.User;
import com.example.moviesBookingApplication.Repository.BookingRepository;
import com.example.moviesBookingApplication.Repository.ShowRepository;
import com.example.moviesBookingApplication.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;


    public Booking createBooking(BookingDTO bookingDTO) {
        Show show = showRepository.findById(bookingDTO.getShowId().getId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        if (isSeatAvailable(show.getId(), bookingDTO.getNumberOfSeats())) {
            throw new RuntimeException("Not enough seat are available");

        }
        if (bookingDTO.getSeatNumbers().size() != bookingDTO.getNumberOfSeats()){
            throw new RuntimeException("Seat Numbers and number of seats must be equal");
        }

        validateDuplicateSeats(show.getId(), bookingDTO.getSeatNumbers());


        User user = userRepository.findById(bookingDTO.getUserId().getId())
                .orElseThrow(()-> new RuntimeException("User not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setSeatNumbers(bookingDTO.getSeatNumbers());
        booking.setPrice(calculateTotalAmount(show.getPrice(), bookingDTO.getNumberOfSeats()));
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.PENDING);

        return  bookingRepository.save(booking);


    }



    private boolean isSeatAvailable(Long showId, Integer numberOfSeats) {
        // Retrieve the show from the repository
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // Synchronize to prevent concurrent access issues
        synchronized (show) {
            // Calculate the number of booked seats
            int bookedSeats = show.getBookings().stream()
                    .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELLED)
                    .mapToInt(Booking::getNumberOfSeats)
                    .sum();

            // Check if enough seats are available
            return (show.getTheater().getTheaterCapacity() + bookedSeats) >= numberOfSeats;
        }
    }







    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public  List<Booking> getShowBookings(Long showid){
        return bookingRepository.findByShowId(showid);
    }

    public Booking confirmBooking(Long bookingid){
       Booking booking = bookingRepository.findById(bookingid)
               .orElseThrow(()-> new RuntimeException("Booking not found"));


       if (booking.getBookingStatus() != BookingStatus.PENDING) {
           throw new RuntimeException("Booking is not in pending state");

       }
           //PAYMENT API PROCESS
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            return  bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingid){

        Booking booking = bookingRepository.findById(bookingid)
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        validateCancellation(booking);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        return  bookingRepository.save(booking);
    }

    public void validateCancellation(Booking booking) {
        LocalDateTime showTime = booking.getShow().getShowTime();
        LocalDateTime deadLineTime = showTime.minusHours(2);


        if (LocalDateTime.now().isAfter(deadLineTime)) {
            throw new RuntimeException("Cannot cancel the booking");
        }
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking Already been cancelled");
        }

    }


    public void validateDuplicateSeats(Long showId, List<String> seatNumbers){

        Show show = showRepository.findById(showId)
                .orElseThrow(()-> new RuntimeException("Show not found"));

        Set<String> occupiedSeats = show.getBookings().stream()
                .filter(b -> b.getBookingStatus() != BookingStatus.CANCELLED)
                .flatMap(b ->b.getSeatNumbers().stream())
                .collect(Collectors.toSet());


        List<String> duplicateSeats = (List<String>) seatNumbers.stream()
                .filter(occupiedSeats::contains)
                .collect(Collectors.toSet());


        if (!duplicateSeats.isEmpty()){
            throw new RuntimeException("Seats are already booked");
        }

    }


    public Double calculateTotalAmount(Double price, Integer numberOfSeats){
        return price * numberOfSeats;
    }



}
