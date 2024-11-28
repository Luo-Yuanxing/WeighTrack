package io.github.weightrack.Service;

import io.github.weightrack.Mapper.UserMapper;
import io.github.weightrack.exception.UserNotFound;
import io.github.weightrack.module.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;  // 注入MyBatis的Mapper

    public User findUserByUsername(String username, String password) throws UserNotFound {
        User user = userMapper.findByUsername(username);
        // 如果查询不到用户，抛出异常
        if (user == null) {
            throw new UserNotFound("未找到此用户");
        }
        if (!user.getPassword().equals(password)) {
            throw new UserNotFound("密码不正确");
        }
        return user;
    }
}
