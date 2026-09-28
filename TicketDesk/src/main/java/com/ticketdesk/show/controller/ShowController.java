package com.ticketdesk.show.controller;

import com.ticketdesk.show.dto.ShowDTO;
import com.ticketdesk.show.service.ShowService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
@CrossOrigin
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }


    // =========================
    // CREATE SHOW
    // =========================

    @PostMapping
    public ResponseEntity<ShowDTO> createShow(
            @Valid @RequestBody ShowDTO dto) {

        return ResponseEntity.ok(
                showService.createShow(dto)
        );
    }


    // =========================
    // GET ALL SHOWS
    // =========================

    @GetMapping
    public ResponseEntity<List<ShowDTO>> getAllShows() {

        return ResponseEntity.ok(
                showService.getAllShows()
        );
    }


    // =========================
    // FILTER SHOWS BY DATE
    // =========================
    // Example:
    // GET /api/shows/search?date=2026-09-30

    @GetMapping("/search")
    public ResponseEntity<List<ShowDTO>> getShowsByDate(
            @RequestParam LocalDate date) {

        return ResponseEntity.ok(
                showService.getShowsByDate(date)
        );
    }


    // =========================
    // GET SHOW BY ID
    // =========================

    @GetMapping("/{id}")
    public ResponseEntity<ShowDTO> getShowById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                showService.getShowById(id)
        );
    }


    // =========================
    // UPDATE SHOW
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<ShowDTO> updateShow(
            @PathVariable Long id,
            @Valid @RequestBody ShowDTO dto) {

        return ResponseEntity.ok(
                showService.updateShow(id, dto)
        );
    }


    // =========================
    // DELETE SHOW
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(
            @PathVariable Long id) {

        showService.deleteShow(id);

        return ResponseEntity.ok(
                "Show deleted successfully"
        );
    }


    // =========================
    // GET REMAINING SEATS
    // =========================

    @GetMapping("/{id}/seats")
    public ResponseEntity<Integer> getRemainingSeats(
            @PathVariable Long id) {

        int remainingSeats =
                showService.getRemainingSeats(id);

        return ResponseEntity.ok(remainingSeats);
    }
}