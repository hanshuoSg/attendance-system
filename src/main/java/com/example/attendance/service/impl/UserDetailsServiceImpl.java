package com.example.attendance.service.impl;

import com.example.attendance.User;
import com.example.attendance.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userDao.findByUsername(username);

        if (dbUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        String role = dbUser.getRole();

        if (role == null || role.trim().isEmpty()) {
            role = "USER";
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(dbUser.getUsername())
                .password(dbUser.getPassword())
                .roles(role)
                .build();
    }
}