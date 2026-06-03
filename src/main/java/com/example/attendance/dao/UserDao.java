package com.example.attendance.dao;

import com.example.attendance.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 新增用户
    public int insert(User user) {
        String sql = "insert into `user` (username, password, real_name, role, email, phone, status) values (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getRealName(),
                user.getRole(),
                user.getEmail(),
                user.getPhone(),
                user.getStatus()
        );
    }

    // 根据ID查询
    public User findById(Long id) {
        String sql = "select id, username, password, real_name, role, create_time, email, phone, status from `user` where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // 查询所有用户
    public List<User> findAll() {
        String sql = "select id, username, password, real_name, role, create_time, email, phone, status from `user`";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    // 根据用户名查询
    public User findByUsername(String username) {
        String sql = "select id, username, password, real_name, role, create_time, email, phone, status from `user` where username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // 更新用户
    public int update(User user) {
        String sql = "update `user` set username = ?, password = ?, real_name = ?, role = ?, email = ?, phone = ?, status = ? where id = ?";
        return jdbcTemplate.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getRealName(),
                user.getRole(),
                user.getEmail(),
                user.getPhone(),
                user.getStatus(),
                user.getId()
        );
    }

    // 删除用户
    public int deleteById(Long id) {
        String sql = "delete from `user` where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}