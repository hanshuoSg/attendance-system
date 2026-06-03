package com.example.attendance.service;
import com.example.attendance.entity.Student;
import java.util.List;

public interface StudentService {
    Student save(Student student);

    Student findById(Long id);

    List<Student> findAll();

    void deleteById(Long id);
}