package com.example.attendance.service;

import com.example.attendance.Student;

public interface StudentService {
    boolean addStudent(Student student);
    Student findByStudentId(String studentId);
}