package com.example.attendance.service;

import com.example.attendance.entity.Attendance;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface AttendanceService {

    Page<Attendance> pageQuery(
            int page,
            int size,
            String sortBy,
            String direction,
            String studentId,
            String status,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}