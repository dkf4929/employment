package com.project.employment.controller;

import com.project.employment.error.ErrorResponse;
import com.project.employment.error.Errors;
import com.project.employment.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler(LoginIdOrPasswordException.class)
    protected ResponseEntity handleException(LoginIdOrPasswordException e) {
        log.error("exception", e);
        Errors error = e.getErrors();
        String errorMessage = Optional.ofNullable(error.getMessage()).orElse(e.getMessage());
        HttpStatus httpStatus = error.getHttpStatus();

        String message = e.getMessage();
        ErrorResponse errorResponse = ErrorResponse.of(message);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handleException(MethodArgumentNotValidException e) {
        log.error("exception", e);
        String message = e.getFieldError() == null ? e.getGlobalError().getDefaultMessage() : e.getFieldError().getDefaultMessage();
        ErrorResponse errorResponse = ErrorResponse.of(message);

        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity handleException(BusinessException e) {
        log.error("exception", e);
        String message = e.getMessage();
        ErrorResponse errorResponse = ErrorResponse.of(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
