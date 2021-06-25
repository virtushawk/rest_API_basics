package com.epam.esm.exception;

public class UserNotFoundException extends CustomServiceException {

    private static final int USER_NOT_FOUND_CODE = 105;
    private static final String USER_NOT_FOUND_MESSAGE = "error.userNotFound";

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getErrorCode() {
        return USER_NOT_FOUND_CODE;
    }

    @Override
    public String getErrorMessage() {
        return USER_NOT_FOUND_MESSAGE;
    }
}
