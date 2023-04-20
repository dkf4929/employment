package com.project.employment.controller;

import com.project.employment.dto.LoginDto;
import com.project.employment.service.LoginService;
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
    @PostMapping
    public String login(LoginDto dto) {
        return loginService.login(dto);
    }
}
