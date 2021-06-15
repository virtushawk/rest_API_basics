package com.epam.esm.controller;

import com.epam.esm.entity.ErrorResponse;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.exception.TagNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

/**
 * The type Exception controller.
 */
@ControllerAdvice
@ResponseBody
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    /**
     * Handle controller not found exception response entity.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(CertificateNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleControllerNotFoundException(CertificateNotFoundException exception, Locale locale) {
        String errorMessage = messageSource.getMessage("error.certificateNotFound", new Object[]{}, locale);
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage + ' ' + exception.getMessage());
        response.setErrorCode(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle tag not found exception response entity.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTagNotFoundException(TagNotFoundException exception, Locale locale) {
        String errorMessage = messageSource.getMessage("error.tagNotFound", new Object[]{}, locale);
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage + ' ' + exception.getMessage());
        response.setErrorCode(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle invalid data form exception response entity.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(InvalidDataFormException.class)
    public ResponseEntity<Object> handleInvalidDataFormException(InvalidDataFormException exception, Locale locale) {
        String errorMessage = messageSource.getMessage("error.invalidDataForm", new Object[]{}, locale);
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage + ' ' + exception.getMessage());
        response.setErrorCode(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
