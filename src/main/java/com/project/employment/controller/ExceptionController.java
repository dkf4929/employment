package com.project.employment.controller;

import com.project.employment.exception.ConfirmPasswordMismatchException;
import com.project.employment.exception.NoSuchUserException;
import com.project.employment.exception.PasswordMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        log.error("exception", e);

        if (e instanceof ConfirmPasswordMismatchException || e instanceof PasswordMismatchException || e instanceof NoSuchUserException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("서버 오류 발생!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
