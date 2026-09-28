package com.ticketdesk.booking.controller;

import com.ticketdesk.booking.dto.BookingDTO;
import com.ticketdesk.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // =========================
    // CREATE BOOKING
    // =========================

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(
            @Valid @RequestBody BookingDTO dto) {

        return ResponseEntity.ok(
                bookingService.createBooking(dto)
        );
    }


    // =========================
    // GET ALL BOOKINGS
    // =========================

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {

        return ResponseEntity.ok(
                bookingService.getAllBookings()
        );
    }


    // =========================
    // GET BOOKING BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }


    // =========================
    // GET BOOKINGS BY SHOW
    // =========================

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByShow(
            @PathVariable Long showId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByShow(showId)
        );
    }


    // =========================
    // CANCEL BOOKING
    // =========================

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingDTO> cancelBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }


    // =========================
    // DELETE BOOKING
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(
            @PathVariable Long id) {

        bookingService.deleteBooking(id);

        return ResponseEntity.ok(
                "Booking deleted successfully"
        );
    }
}