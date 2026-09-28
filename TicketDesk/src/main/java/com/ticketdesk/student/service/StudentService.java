package com.ticketdesk.student.service;

import com.ticketdesk.student.dto.StudentDTO;
import com.ticketdesk.student.entity.Student;
import com.ticketdesk.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // ==========================================
    // CREATE STUDENT
    // ==========================================

    public StudentDTO createStudent(StudentDTO dto) {

        Student student = new Student();

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setDepartment(dto.getDepartment());

        Student savedStudent = repository.save(student);

        return convertToDTO(savedStudent);
    }

    // ==========================================
    // GET ALL STUDENTS
    // ==========================================

    public List<StudentDTO> getAllStudents() {

        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // ==========================================
    // GET STUDENT BY ID
    // ==========================================

    public StudentDTO getStudentById(Long id) {

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"
                        )
                );

        return convertToDTO(student);
    }

    // ==========================================
    // UPDATE STUDENT
    // ==========================================

    public StudentDTO updateStudent(
            Long id,
            StudentDTO dto) {

        Student student = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Student not found"
                        )
                );

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setDepartment(dto.getDepartment());

        Student updatedStudent =
                repository.save(student);

        return convertToDTO(updatedStudent);
    }

    // ==========================================
    // DELETE STUDENT
    // ==========================================

    public void deleteStudent(Long id) {

        if (!repository.existsById(id)) {

            throw new RuntimeException(
                    "Student not found"
            );
        }

        repository.deleteById(id);
    }

    // ==========================================
    // ENTITY → DTO
    // ==========================================

    private StudentDTO convertToDTO(Student student) {

        StudentDTO dto = new StudentDTO();

        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setDepartment(student.getDepartment());

        return dto;
    }
}