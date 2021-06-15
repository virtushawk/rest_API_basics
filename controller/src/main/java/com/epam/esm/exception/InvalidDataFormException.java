package com.epam.esm.exception;

public class InvalidDataFormException extends RuntimeException {

    private int errorCode = 102;

    public InvalidDataFormException() {
    }

    public InvalidDataFormException(String message) {
        super(message);
    }

    public InvalidDataFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataFormException(Throwable cause) {
        super(cause);
    }

    public InvalidDataFormException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
