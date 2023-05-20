package com.project.employment.exception;

import com.project.employment.error.Errors;
import lombok.Getter;

import org.springframework.security.core.AuthenticationException;

@Getter
public class LoginIdOrPasswordException extends RuntimeException {
    private Errors errors;

    public LoginIdOrPasswordException() {
        super(Errors.BAD_LOGIN_CREDENTIALS.getMessage());
        this.errors = Errors.BAD_LOGIN_CREDENTIALS;
    }
}
