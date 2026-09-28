package com.ticketdesk.booking.repository;

import com.ticketdesk.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Check whether a particular seat is already booked
    boolean existsByShowIdAndSeatNumberAndStatus(
            Long showId,
            String seatNumber,
            String status
    );

    // Get all bookings for a particular show
    List<Booking> findByShowId(Long showId);

    // Count bookings with a particular status for a show
    long countByShowIdAndStatus(
            Long showId,
            String status
    );
}