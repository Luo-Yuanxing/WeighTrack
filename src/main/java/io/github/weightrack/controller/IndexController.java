package io.github.weightrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(
            Model model
    ) {
        model.addAttribute("name", "lyx");
        System.out.println("index into");
        System.out.println(model.getAttribute("name"));
        return "index";
    }
}
