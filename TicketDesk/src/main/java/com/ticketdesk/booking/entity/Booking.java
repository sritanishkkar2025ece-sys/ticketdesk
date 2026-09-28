package com.ticketdesk.booking.entity;

import com.ticketdesk.show.entity.Show;
import com.ticketdesk.student.entity.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @NotBlank(message = "Seat number is required")
    @Column(nullable = false)
    private String seatNumber;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @NotBlank(message = "Booking status is required")
    @Column(nullable = false)
    private String status;
}