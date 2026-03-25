package com.example.attendance.dao;

import com.example.attendance.Student;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDao {

    private List<Student> studentList = new ArrayList<>();

    public StudentDao() {
        studentList.add(new Student("42411201", "韩硕", "计算机3班", 20));
        studentList.add(new Student("2024002", "李四", "计算机1班", 21));
        studentList.add(new Student("2024003", "王五", "计算机1班", 20));
        studentList.add(new Student("2024004", "赵六", "计算机2班", 22));
        studentList.add(new Student("2024005", "钱七", "计算机2班", 21));
        studentList.add(new Student("2024006", "孙八", "计算机2班", 20));
    }

    public int insert(Student student) {
        studentList.add(student);
        return 1;
    }

    public Student findByStudentId(String studentId) {
        for (Student student : studentList) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> findAll() {
        return studentList;
    }
}