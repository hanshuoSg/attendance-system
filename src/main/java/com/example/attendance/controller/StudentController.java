package com.example.attendance.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.attendance.Result;
import com.example.attendance.Student;
import com.example.attendance.Attendance;
import com.example.attendance.service.StudentService;

@RestController
public class StudentController {

    private List<Student> studentList = new ArrayList<>();

    @Autowired
    private StudentService studentService;

    public StudentController() {
        studentList.add(new Student("42411201", "韩硕", "计算机3班", 20));
        studentList.add(new Student("2024002", "李四", "计算机1班", 21));
        studentList.add(new Student("2024003", "王五", "计算机1班", 20));
        studentList.add(new Student("2024004", "赵六", "计算机2班", 22));
        studentList.add(new Student("2024005", "钱七", "计算机2班", 21));
        studentList.add(new Student("2024006", "孙八", "计算机2班", 20));
    }

    // ========== 原有接口 ==========

    @GetMapping("/student/info")
    public String getStudentInfo() {
        return "Name: Han Shuo, Student ID: 42411201, Class: Computer Science Class 3";
    }

    @PostMapping("/student/attendance")
    public String attendance(@RequestBody Map<String, String> request) {
        String studentId = request.get("studentId");
        return "Student " + studentId + " checked in successfully!";
    }

    @GetMapping("/student/courses")
    public List<String> getCourses() {
        return Arrays.asList("Database Principles", "Computer Networks", "Discrete Mathematics");
    }

    @GetMapping("/student/info/{studentId}")
    public Result<Student> getStudentByPath(@PathVariable String studentId) {
        for (Student student : studentList) {
            if (student.getStudentId().equals(studentId)) {
                return Result.success(student);
            }
        }
        return Result.error("Student not found with ID: " + studentId);
    }

    @GetMapping("/student/list")
    public Result<Map<String, Object>> getStudentList(
            @RequestParam String className,
            @RequestParam(defaultValue = "1") int page) {

        List<Student> filteredStudents = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getClassName().equals(className)) {
                filteredStudents.add(student);
            }
        }

        int pageSize = 2;
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, filteredStudents.size());

        if (start >= filteredStudents.size()) {
            Map<String, Object> emptyResult = new HashMap<>();
            emptyResult.put("students", new ArrayList<>());
            emptyResult.put("total", filteredStudents.size());
            emptyResult.put("currentPage", page);
            emptyResult.put("pageSize", pageSize);
            emptyResult.put("totalPages", (int) Math.ceil((double) filteredStudents.size() / pageSize));
            return Result.success(emptyResult);
        }

        List<Student> pageStudents = filteredStudents.subList(start, end);

        Map<String, Object> result = new HashMap<>();
        result.put("students", pageStudents);
        result.put("total", filteredStudents.size());
        result.put("currentPage", page);
        result.put("pageSize", pageSize);
        result.put("totalPages", (int) Math.ceil((double) filteredStudents.size() / pageSize));

        return Result.success(result);
    }

    @PostMapping("/attendance/update")
    public Result<String> updateAttendance(@RequestBody Attendance attendance) {
        String message = "Attendance record updated successfully: Student ID " + attendance.getStudentId()
                + ", Name " + attendance.getStudentName()
                + ", Status " + attendance.getStatus();
        return Result.success(message);
    }

    // ========== 新增的分层架构接口 ==========

    @PostMapping("/api/student/add")
    public Result<String> addStudent(@RequestBody Student student) {
        try {
            boolean success = studentService.addStudent(student);
            if (success) {
                return Result.success("学生添加成功：" + student.getName());
            } else {
                return Result.error("学生添加失败");
            }
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("系统错误：" + e.getMessage());
        }
    }

    @GetMapping("/api/student/{studentId}")
    public Result<Student> getStudent(@PathVariable String studentId) {
        Student student = studentService.findByStudentId(studentId);
        if (student != null) {
            return Result.success(student);
        } else {
            return Result.error("未找到学号为 " + studentId + " 的学生");
        }
    }
}