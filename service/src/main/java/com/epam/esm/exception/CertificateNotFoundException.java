package com.epam.esm.exception;

public class CertificateNotFoundException extends CustomServiceException {

    private static final int CERTIFICATE_NOT_FOUND_ERROR_CODE = 103;
    private static final String CERTIFICATE_NOT_FOUND_MESSAGE = "error.certificateNotFound";

    public CertificateNotFoundException() {
    }

    public CertificateNotFoundException(String message) {
        super(message);
    }

    public CertificateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CertificateNotFoundException(Throwable cause) {
        super(cause);
    }

    public CertificateNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getErrorCode() {
        return CERTIFICATE_NOT_FOUND_ERROR_CODE;
    }

    @Override
    public String getErrorMessage() {
        return CERTIFICATE_NOT_FOUND_MESSAGE;
    }
}
