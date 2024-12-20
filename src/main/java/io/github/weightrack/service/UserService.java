package io.github.weightrack.service;

import io.github.weightrack.exception.UsersException;
import io.github.weightrack.mapper.UserMapper;
import io.github.weightrack.module.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;  // 注入MyBatis的Mapper

    public User findUserByUsername(String username, String password) throws UsersException {
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

    public User insertUser(String username, String password, String realName) throws UsersException {
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
