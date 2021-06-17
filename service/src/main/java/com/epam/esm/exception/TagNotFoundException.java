package com.epam.esm.exception;

public class TagNotFoundException extends CustomServiceException {

    private static final int TAG_NOT_FOUND_ERROR_CODE = 104;

    public TagNotFoundException() {
    }

    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagNotFoundException(Throwable cause) {
        super(cause);
    }

    public TagNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getErrorCode() {
        return TAG_NOT_FOUND_ERROR_CODE;
    }
}
