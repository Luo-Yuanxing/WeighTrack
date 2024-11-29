package io.github.weightrack.controller;

import io.github.weightrack.Service.UserService;
import io.github.weightrack.exception.UserNotFound;
import io.github.weightrack.module.PoundBillModel;
import io.github.weightrack.module.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        try {
            User user = userService.findUserByUsername(username, password);
            request.getSession().setAttribute("user", user);
        } catch (UserNotFound userNotFound) {
            model.addAttribute("error", userNotFound.getMessage());
            return "/login";
        }
        return "redirect:/";
    }
}
