package io.github.weightrack.service;

import io.github.weightrack.exception.UsersException;
import io.github.weightrack.mapper.UserMapper;
import io.github.weightrack.module.User;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;  // 注入MyBatis的Mapper

    public User findUserByUsernameAndPassword(@NotNull String username, @NotNull String password) throws UsersException {
        if (username.isEmpty()) {
            throw new UsersException("用户名不能为空");
        }
        if (password.isEmpty()) {
            throw new UsersException("密码不能为空");
        }
        User[] users = userMapper.findByUsername(username);
        if (users.length == 0) {
            throw new UsersException("未找到此用户");
        }
        if (!users[0].getPassword().equals(password)) {
            throw new UsersException("密码不正确");
        }
        userMapper.updateLastLogin(users[0].getUsername());
        return users[0];
    }

    public User insertUser(@NotNull User user) throws UsersException {
        if (user.getUsername().isEmpty()) {
            throw new UsersException("用户名不能为空");
        }
        if (user.getPassword().isEmpty()) {
            throw new UsersException("密码不能为空");
        }
        if (user.getRealName().isEmpty()) {
            throw new UsersException("司磅员名称不能为空");
        }
        if (userMapper.findByUsername(user.getUsername()).length > 0) {
            userMapper.updateLastLogin(user.getUsername());
            throw new UsersException("此用户名已存在");
        }
        if (userMapper.findByRealName(user.getRealName()).length > 0) {
            throw new UsersException("此司磅员已存在，请联系管理员修改账户名或密码");
        }
        if (userMapper.insertUser(user) == 1) {
            userMapper.updateLastLogin(user.getUsername());
            return userMapper.findByUsername(user.getUsername())[0];
        } else {
            throw new UsersException("新增用户失败");
        }
    }

    public User[] findAll() {
        return userMapper.getAllUsers();
    }

    public int delete(String username) {
        return userMapper.delete(username);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public User findUserByUsername(String administrators) throws UsersException {
        User[] users = userMapper.findByUsername(administrators);
        if (users.length == 0) {
            return null;
        } else if (users.length == 1) {
            return users[0];
        } else {
            log.error("数据库中存在多个相同的用户名");
            throw new UsersException("数据库中存在多个相同的用户名");
        }
    }

    public int updateById(User user) {
        return userMapper.updateById(user);
    }

    public int deleteById(int deleteId) {
        return userMapper.deleteById(deleteId);
    }

    public User findUserById(int id) {
        return userMapper.findById(id);
    }
}
