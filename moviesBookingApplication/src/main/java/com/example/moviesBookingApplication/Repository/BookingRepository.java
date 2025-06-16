package com.example.moviesBookingApplication.Repository;

import com.example.moviesBookingApplication.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findByUserId(Long userId);
    List<Booking> findByShowId(Long showId);
}
