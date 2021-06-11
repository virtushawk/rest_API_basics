package com.epam.esm.exception;

public class InvalidDataFormException extends RuntimeException {

    public static int INVALID_DATA_FROM_ERROR_CODE = 102;

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
}
