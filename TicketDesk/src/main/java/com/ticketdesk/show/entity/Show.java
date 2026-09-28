package com.ticketdesk.show.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Show title is required")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Show time is required")
    @Column(nullable = false)
    private LocalDateTime showTime;

    @NotNull(message = "Total seats are required")
    @Column(nullable = false)
    private Integer totalSeats;
}