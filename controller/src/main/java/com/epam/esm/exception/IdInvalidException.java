package com.epam.esm.exception;

public class IdInvalidException extends RuntimeException {

    private int errorCode = 101;
    private Long id;

    public IdInvalidException() {
    }

    public IdInvalidException(String message) {
        super(message);
    }

    public IdInvalidException(Long id) {
        this.id = id;
    }

    public IdInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdInvalidException(Throwable cause) {
        super(cause);
    }

    public IdInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Long getId() {
        return id;
    }
}
