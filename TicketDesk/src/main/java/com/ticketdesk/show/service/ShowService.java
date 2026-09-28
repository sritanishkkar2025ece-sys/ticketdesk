package com.ticketdesk.show.service;

import com.ticketdesk.booking.repository.BookingRepository;
import com.ticketdesk.exception.ResourceNotFoundException;
import com.ticketdesk.show.dto.ShowDTO;
import com.ticketdesk.show.entity.Show;
import com.ticketdesk.show.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    private final ShowRepository repository;
    private final BookingRepository bookingRepository;

    public ShowService(
            ShowRepository repository,
            BookingRepository bookingRepository) {

        this.repository = repository;
        this.bookingRepository = bookingRepository;
    }


    // =========================
    // CREATE SHOW
    // =========================

    public ShowDTO createShow(ShowDTO dto) {

        Show show = new Show();

        show.setTitle(dto.getTitle());
        show.setShowTime(dto.getTime());
        show.setTotalSeats(dto.getTotalSeats());

        Show savedShow = repository.save(show);

        return convertToDTO(savedShow);
    }


    // =========================
    // GET ALL SHOWS
    // =========================

    public List<ShowDTO> getAllShows() {

        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // =========================
    // GET SHOW BY ID
    // =========================

    public ShowDTO getShowById(Long id) {

        Show show = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: " + id
                        )
                );

        return convertToDTO(show);
    }


    // =========================
    // UPDATE SHOW
    // =========================

    public ShowDTO updateShow(
            Long id,
            ShowDTO dto) {

        Show show = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: " + id
                        )
                );

        show.setTitle(dto.getTitle());
        show.setShowTime(dto.getTime());
        show.setTotalSeats(dto.getTotalSeats());

        Show updatedShow = repository.save(show);

        return convertToDTO(updatedShow);
    }


    // =========================
    // DELETE SHOW
    // =========================

    public void deleteShow(Long id) {

        Show show = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: " + id
                        )
                );

        repository.delete(show);
    }


    // =========================
    // FILTER SHOWS BY DATE
    // =========================

    public List<ShowDTO> getShowsByDate(LocalDate date) {

        LocalDateTime start =
                date.atStartOfDay();

        LocalDateTime end =
                date.plusDays(1)
                        .atStartOfDay()
                        .minusNanos(1);

        return repository
                .findByShowTimeBetween(start, end)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    // =========================
    // GET REMAINING SEATS
    // =========================

    public int getRemainingSeats(Long id) {

        Show show = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: " + id
                        )
                );

        long confirmedBookings =
                bookingRepository.countByShowIdAndStatus(
                        id,
                        "CONFIRMED"
                );

        return (int) (
                show.getTotalSeats() - confirmedBookings
        );
    }


    // =========================
    // ENTITY → DTO
    // =========================

    private ShowDTO convertToDTO(Show show) {

        ShowDTO dto = new ShowDTO();

        // Important: send ID to frontend
        dto.setId(show.getId());

        dto.setTitle(show.getTitle());
        dto.setTime(show.getShowTime());
        dto.setTotalSeats(show.getTotalSeats());

        return dto;
    }
}