package com.epam.esm.controller;

import com.epam.esm.entity.ErrorResponse;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.exception.TagNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
@AllArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    private static final int INTERNAL_SERVER_ERROR_CODE = 100;

    /**
     * Handles the certificateNotFoundException class
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
     * Handles the TagNotFoundException class
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
     * Handles the InvalidDataFromException class
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(InvalidDataFormException.class)
    public ResponseEntity<Object> handleInvalidDataFormException(InvalidDataFormException exception, Locale locale) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(messageSource.getMessage("error.invalidDataForm", new Object[]{}, locale));
        for (ObjectError object : exception.getBindingResult().getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                errorMessage.append(messageSource.getMessage(fieldError, locale)).append(',');
            }
        }
        errorMessage.deleteCharAt(errorMessage.length() - 1);
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage.toString());
        response.setErrorCode(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the RuntimeException class
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleInternalServerError(RuntimeException exception, Locale locale) {
        String errorMessage = messageSource.getMessage("error.internalServerError", new Object[]{}, locale);
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage);
        response.setErrorCode(INTERNAL_SERVER_ERROR_CODE);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles the IdInvalidException class
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(IdInvalidException.class)
    public ResponseEntity<Object> handleIdInvalidException(IdInvalidException exception, Locale locale) {
        String errorMessage = messageSource.getMessage("error.idInvalidError", new Object[]{}, locale);
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage + " " + exception.getId());
        response.setErrorCode(exception.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
