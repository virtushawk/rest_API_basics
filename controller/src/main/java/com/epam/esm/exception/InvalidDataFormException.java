package com.epam.esm.exception;

import org.springframework.validation.BindingResult;

public class InvalidDataFormException extends CustomServiceException {

    private static final int INVALID_DATA_FORM_ERROR_CODE = 102;
    private BindingResult bindingResult;

    public InvalidDataFormException() {}

    public InvalidDataFormException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
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

    @Override
    public int getErrorCode() {
        return INVALID_DATA_FORM_ERROR_CODE;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
