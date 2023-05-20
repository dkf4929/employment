package com.project.employment.exception;

import com.project.employment.error.Errors;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Executable;

public class InvalidFieldException extends MethodArgumentNotValidException {
    private Errors errors;

    public InvalidFieldException(MethodParameter parameter, BindingResult bindingResult) {
        super(parameter, bindingResult);
    }
}
