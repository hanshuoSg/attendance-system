package com.example.attendance.service;

import com.example.attendance.User;

import java.util.List;

public interface UserService {
    int addUser(User user);

    User getById(Long id);

    List<User> getAll();

    User getByUsername(String username);

    int updateUser(User user);

    int deleteById(Long id);
    int register(User user);
}