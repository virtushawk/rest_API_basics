package com.epam.esm.exception;

public class IdInvalidException extends CustomServiceException {

    private static final int ID_INVALID_ERROR_CODE = 101;
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

    @Override
    public int getErrorCode() {
        return ID_INVALID_ERROR_CODE;
    }

    public Long getId() {
        return id;
    }
}
