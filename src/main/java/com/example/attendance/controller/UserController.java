package com.example.attendance.controller;

import com.example.attendance.Result;
import com.example.attendance.User;
import com.example.attendance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 新增用户
    @PostMapping
    public Result addUser(@RequestBody User user) {
        int rows = userService.addUser(user);
        if (rows > 0) {
            return Result.success("新增成功");
        }
        return Result.error("新增失败");
    }

    // 根据ID查询
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }

    // 查询所有用户
    @GetMapping
    public Result getAll() {
        return Result.success(userService.getAll());
    }

    // 根据用户名查询
    @GetMapping("/by-username")
    public Result getByUsername(@RequestParam String username) {
        User user = userService.getByUsername(username);
        if (user != null) {
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }

    // 更新用户
    @PutMapping("/{id}")
    public Result updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        int rows = userService.updateUser(user);
        if (rows > 0) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable Long id) {
        int rows = userService.deleteById(id);
        if (rows > 0) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
    @PostMapping("/auth/register")
    public Result register(@RequestBody User user) {
        int rows = userService.register(user);
        if (rows > 0) {
            return Result.success("注册成功");
        }
        return Result.error("注册失败");
    }
}