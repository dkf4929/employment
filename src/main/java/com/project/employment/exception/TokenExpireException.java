package com.project.employment.exception;

public class TokenExpireException extends RuntimeException{
    public TokenExpireException() {
        super();
    }

    public TokenExpireException(String message) {
        super(message);
    }

    public TokenExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExpireException(Throwable cause) {
        super(cause);
    }
}
