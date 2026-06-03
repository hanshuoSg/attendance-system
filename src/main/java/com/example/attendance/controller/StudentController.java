package com.example.attendance.controller;
import com.example.attendance.entity.Student;
import com.example.attendance.Result;
import com.example.attendance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 新增学生
    @PostMapping
    public Result addStudent(@RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        return Result.success(savedStudent);
    }

    // 根据ID查询学生
    @GetMapping("/{id}")
    public Result getStudentById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        if (student != null) {
            return Result.success(student);
        }
        return Result.error("学生不存在");
    }

    // 查询所有学生
    @GetMapping
    public Result getAllStudents() {
        return Result.success(studentService.findAll());
    }

    // 更新学生
    @PutMapping("/{id}")
    public Result updateStudent(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        Student updatedStudent = studentService.save(student);
        return Result.success(updatedStudent);
    }

    // 删除学生
    @DeleteMapping("/{id}")
    public Result deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return Result.success("删除成功");
    }
}