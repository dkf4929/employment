package com.project.employment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRq {
    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}