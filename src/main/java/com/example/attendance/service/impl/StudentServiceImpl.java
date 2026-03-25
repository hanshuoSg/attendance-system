package com.example.attendance.service.impl;

import com.example.attendance.Student;
import com.example.attendance.dao.StudentDao;
import com.example.attendance.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public boolean addStudent(Student student) {
        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            throw new IllegalArgumentException("学号不能为空");
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不能为空");
        }
        if (student.getClassName() == null || student.getClassName().trim().isEmpty()) {
            throw new IllegalArgumentException("班级不能为空");
        }

        Student existing = studentDao.findByStudentId(student.getStudentId());
        if (existing != null) {
            throw new IllegalArgumentException("学号 " + student.getStudentId() + " 已存在");
        }

        return studentDao.insert(student) > 0;
    }

    @Override
    public Student findByStudentId(String studentId) {
        return studentDao.findByStudentId(studentId);
    }
}