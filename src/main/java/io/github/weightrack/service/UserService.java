package io.github.weightrack.service;

import io.github.weightrack.exception.UsersException;
import io.github.weightrack.mapper.UserMapper;
import io.github.weightrack.module.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;  // 注入MyBatis的Mapper

    public User findUserByUsername(@NotNull String username, @NotNull String password) throws UsersException {
        if (username.isEmpty()) {
            throw new UsersException("用户名不能为空");
        }
        if (password.isEmpty()) {
            throw new UsersException("密码不能为空");
        }
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsersException("未找到此用户");
        }
        if (!user.getPassword().equals(password)) {
            throw new UsersException("密码不正确");
        }
        userMapper.updateLastLogin(user.getUsername());
        return user;
    }

    public User insertUser(@NotNull String username, @NotNull String password, @NotNull String realName) throws UsersException {
        if (username.isEmpty()) {
            throw new UsersException("用户名不能为空");
        }
        if (password.isEmpty()) {
            throw new UsersException("密码不能为空");
        }
        if (realName.isEmpty()) {
            throw new UsersException("司磅员名称不能为空");
        }
        User user = userMapper.findByUsername(username);
        if (user != null) {
            userMapper.updateLastLogin(username);
            throw new UsersException("此用户名已存在");
        }
        if (userMapper.insertUser(username, password, realName) == 1) {
            userMapper.updateLastLogin(username);
            return userMapper.findByUsername(username);
        } else {
            throw new UsersException("新增用户失败");
        }
    }

    public User[] findAll() {
        return userMapper.findAll();
    }

    public int delete(String username) {
        return userMapper.delete(username);
    }
}
