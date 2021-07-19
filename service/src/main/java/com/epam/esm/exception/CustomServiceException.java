package com.epam.esm.exception;

public abstract class CustomServiceException extends RuntimeException {

    protected CustomServiceException() {
    }

    protected CustomServiceException(String message) {
        super(message);
    }

    protected CustomServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CustomServiceException(Throwable cause) {
        super(cause);
    }

    protected CustomServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public abstract int getErrorCode();

    public abstract String getErrorMessage();

}
