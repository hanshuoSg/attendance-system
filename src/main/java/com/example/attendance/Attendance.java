package com.example.attendance;

import java.time.LocalDateTime;

public class Attendance {
    private String studentId;
    private String studentName;
    private LocalDateTime checkTime;
    private String status;

    public Attendance() {}

    public Attendance(String studentId, String studentName, LocalDateTime checkTime, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.checkTime = checkTime;
        this.status = status;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public LocalDateTime getCheckTime() { return checkTime; }
    public void setCheckTime(LocalDateTime checkTime) { this.checkTime = checkTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}