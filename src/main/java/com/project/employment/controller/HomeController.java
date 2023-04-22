package com.project.employment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }
}
