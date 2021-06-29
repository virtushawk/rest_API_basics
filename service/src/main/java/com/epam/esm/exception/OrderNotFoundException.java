package com.epam.esm.exception;

public class OrderNotFoundException extends CustomServiceException {

    private static final int ORDER_NOT_FOUND_ERROR_CODE = 107;
    private static final String ORDER_NOT_FOUND_MESSAGE = "error.orderNotFound";

    public OrderNotFoundException() {
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException(Throwable cause) {
        super(cause);
    }

    public OrderNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getErrorCode() {
        return ORDER_NOT_FOUND_ERROR_CODE;
    }

    @Override
    public String getErrorMessage() {
        return ORDER_NOT_FOUND_MESSAGE;
    }
}
