package com.ticketdesk.student.repository;

import com.ticketdesk.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository
        extends JpaRepository<Student, Long> {
}
