package io.github.weightrack.controller;

import com.alibaba.fastjson2.JSON;
import io.github.weightrack.dto.LogonDTO;
import io.github.weightrack.dto.SignInDTO;
import io.github.weightrack.exception.UsersException;
import io.github.weightrack.module.User;
import io.github.weightrack.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestBody SignInDTO signInDTO) {
        LogonDTO logonDTO = new LogonDTO();
        try {
            User user = userService.findUserByUsernameAndPassword(signInDTO.getUsername(), signInDTO.getPassword());
            request.getSession().setAttribute("user", user);
            log.info("{} 登录成功", user.getUsername());
            logonDTO.setUser(user);
            return JSON.toJSONString(logonDTO);
        } catch (UsersException userNotFound) {
            log.info("{} 尝试登录失败", signInDTO.getUsername());
            logonDTO.setUser(null);
            logonDTO.setMessage(userNotFound.getMessage());
            return JSON.toJSONString(logonDTO);
        }
    }

    @ResponseBody
    @PostMapping("/signup")
    public String signup(HttpServletRequest request, @RequestBody SignInDTO signInDTO) {
        LogonDTO logonDTO = new LogonDTO();
        try {
            User user = userService.insertUser(new User(signInDTO.getUsername(), signInDTO.getPassword(), signInDTO.getRealName(), "normal"));
            request.getSession().setAttribute("user", user);
            log.info("{} 注册成功", user.getUsername());
            logonDTO.setUser(user);
            logonDTO.setMessage("注册成功");
            return JSON.toJSONString(logonDTO);
        } catch (UsersException usersException) {
            log.info("{} 尝试注册失败，失败原因：{}", signInDTO.getUsername(), usersException.getMessage());
            logonDTO.setMessage(usersException.getMessage());
            return JSON.toJSONString(logonDTO);
        }
    }

    @ResponseBody
    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
