package com.example.attendance.repository;

import com.example.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer>, JpaSpecificationExecutor<Attendance> {

    boolean existsByStudentIdAndCourseIdAndCheckInTimeBetween(
            String studentId,
            String courseId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}