package com.project.employment.exception;

public class ConfirmPasswordMismatchException extends RuntimeException {
    public ConfirmPasswordMismatchException() {
        super();
    }

    public ConfirmPasswordMismatchException(String message) {
        super(message);
    }

    public ConfirmPasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmPasswordMismatchException(Throwable cause) {
        super(cause);
    }
}
