package com.project.employment.controller;

import com.project.employment.exception.CommonException;
import com.project.employment.exception.ConfirmPasswordMismatchException;
import com.project.employment.exception.NoSuchUserException;
import com.project.employment.exception.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        log.error("exception", e);

        if (e instanceof CommonException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (e instanceof DataIntegrityViolationException) {
            return new ResponseEntity<>("중복된 값이 있습니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("서버 오류 발생!!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
