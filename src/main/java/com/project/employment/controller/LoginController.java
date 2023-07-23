package com.project.employment.controller;

import com.project.employment.dto.LoginRq;
import com.project.employment.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @ResponseBody
    @PostMapping("/priv")
    public String login(@Valid LoginRq loginRq) {
        return loginService.login(loginRq);
    }
}
