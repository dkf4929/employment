package com.project.employment.exception;

import com.project.employment.error.Errors;

public class BadRequestException extends CommonException{
    Errors errors;

    public BadRequestException() {
        super(Errors.BAD_REQUEST.getMessage());
        this.errors = Errors.BAD_REQUEST;
    }
}
