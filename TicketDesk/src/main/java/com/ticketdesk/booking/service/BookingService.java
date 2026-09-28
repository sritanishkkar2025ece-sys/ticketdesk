package com.ticketdesk.booking.service;

import com.ticketdesk.booking.dto.BookingDTO;
import com.ticketdesk.booking.entity.Booking;
import com.ticketdesk.booking.repository.BookingRepository;
import com.ticketdesk.exception.ResourceNotFoundException;
import com.ticketdesk.show.entity.Show;
import com.ticketdesk.show.repository.ShowRepository;
import com.ticketdesk.student.entity.Student;
import com.ticketdesk.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final StudentRepository studentRepository;
    private final ShowRepository showRepository;

    public BookingService(
            BookingRepository bookingRepository,
            StudentRepository studentRepository,
            ShowRepository showRepository) {

        this.bookingRepository = bookingRepository;
        this.studentRepository = studentRepository;
        this.showRepository = showRepository;
    }

    // =========================
    // CREATE BOOKING
    // =========================

    public BookingDTO createBooking(BookingDTO dto) {

        // Find student
        Student student = studentRepository
                .findById(dto.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id: "
                                        + dto.getStudentId()
                        )
                );

        // Find show
        Show show = showRepository
                .findById(dto.getShowId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: "
                                        + dto.getShowId()
                        )
                );

        // =========================
        // CHECK TOTAL SEAT LIMIT
        // =========================

        long confirmedBookings =
                bookingRepository.countByShowIdAndStatus(
                        dto.getShowId(),
                        "CONFIRMED"
                );

        if (confirmedBookings >= show.getTotalSeats()) {

            throw new RuntimeException(
                    "No seats available for this show"
            );
        }

        // =========================
        // CHECK DUPLICATE SEAT
        // =========================

        boolean seatAlreadyBooked =
                bookingRepository
                        .existsByShowIdAndSeatNumberAndStatus(
                                dto.getShowId(),
                                dto.getSeatNumber(),
                                "CONFIRMED"
                        );

        if (seatAlreadyBooked) {

            throw new RuntimeException(
                    "Seat " + dto.getSeatNumber()
                            + " is already booked for this show"
            );
        }

        // =========================
        // CREATE BOOKING
        // =========================

        Booking booking = new Booking();

        booking.setStudent(student);
        booking.setShow(show);
        booking.setSeatNumber(dto.getSeatNumber());
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("CONFIRMED");

        Booking savedBooking =
                bookingRepository.save(booking);

        return convertToDTO(savedBooking);
    }


    // =========================
    // GET ALL BOOKINGS
    // =========================

    public List<BookingDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    // =========================
    // GET BOOKING BY ID
    // =========================

    public BookingDTO getBookingById(Long id) {

        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + id
                        )
                );

        return convertToDTO(booking);
    }


    // =========================
    // GET BOOKINGS BY SHOW
    // =========================

    public List<BookingDTO> getBookingsByShow(
            Long showId) {

        if (!showRepository.existsById(showId)) {

            throw new ResourceNotFoundException(
                    "Show not found with id: " + showId
            );
        }

        return bookingRepository
                .findByShowId(showId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    // =========================
    // CANCEL BOOKING
    // =========================

    public BookingDTO cancelBooking(Long id) {

        Booking booking = bookingRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found with id: " + id
                        )
                );

        booking.setStatus("CANCELLED");

        Booking updatedBooking =
                bookingRepository.save(booking);

        return convertToDTO(updatedBooking);
    }


    // =========================
    // DELETE BOOKING
    // =========================

    public void deleteBooking(Long id) {

        if (!bookingRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Booking not found with id: " + id
            );
        }

        bookingRepository.deleteById(id);
    }


    // =========================
    // GET REMAINING SEATS
    // =========================

    public int getRemainingSeats(Long showId) {

        Show show = showRepository
                .findById(showId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: " + showId
                        )
                );

        long confirmedBookings =
                bookingRepository.countByShowIdAndStatus(
                        showId,
                        "CONFIRMED"
                );

        return (int) (
                show.getTotalSeats() - confirmedBookings
        );
    }


    // =========================
    // ENTITY → DTO
    // =========================

    private BookingDTO convertToDTO(
            Booking booking) {

        return new BookingDTO(
                booking.getId(),
                booking.getStudent().getId(),
                booking.getShow().getId(),
                booking.getSeatNumber(),
                booking.getStatus()
        );
    }
}