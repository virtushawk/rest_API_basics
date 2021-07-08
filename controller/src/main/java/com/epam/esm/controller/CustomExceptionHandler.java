package com.epam.esm.controller;

import com.epam.esm.entity.ErrorResponse;
import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.IdInvalidException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.exception.OrderNotFoundException;
import com.epam.esm.exception.TagNotFoundException;
import com.epam.esm.exception.UserNotFoundException;
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

import java.util.List;
import java.util.Locale;

/**
 * The type Exception controller.
 */
@ControllerAdvice
@ResponseBody
@AllArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    private static final int INTERNAL_SERVER_ERROR_CODE = 100;
    private static final String INTERNAL_SERVER_CODE = "error.internalServerError";
    private static final String SPACE_DELIMITER = " ";
    private static final String COMMA_DELIMITER = ",";

    /**
     * Handles the certificateNotFoundException class
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(CertificateNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCertificateNotFoundException(CertificateNotFoundException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale);
        String errorMessage = message + SPACE_DELIMITER + exception.getMessage();
        return new ResponseEntity<>(createErrorResponse(errorMessage, exception.getErrorCode()), HttpStatus.NOT_FOUND);
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
        String message = messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale);
        String errorMessage = message + SPACE_DELIMITER + exception.getMessage();
        return new ResponseEntity<>(createErrorResponse(errorMessage, exception.getErrorCode()), HttpStatus.NOT_FOUND);
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
        errorMessage.append(messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale));
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        errors.stream().filter(FieldError.class::isInstance)
                .forEach(objectError -> errorMessage
                        .append(SPACE_DELIMITER)
                        .append(messageSource.getMessage(objectError, locale))
                        .append(COMMA_DELIMITER));
        errorMessage.deleteCharAt(errorMessage.length() - 1);
        return new ResponseEntity<>(createErrorResponse(errorMessage.toString(), exception.getErrorCode()), HttpStatus.BAD_REQUEST);
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
        String errorMessage = messageSource.getMessage(INTERNAL_SERVER_CODE, new Object[]{}, locale);
        return new ResponseEntity<>(createErrorResponse(errorMessage, INTERNAL_SERVER_ERROR_CODE), HttpStatus.INTERNAL_SERVER_ERROR);
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
        String message = messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale);
        String errorMessage = message + SPACE_DELIMITER + exception.getId();
        return new ResponseEntity<>(createErrorResponse(errorMessage, exception.getErrorCode()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles the UserNotFoundException class
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale);
        String errorMessage = message + SPACE_DELIMITER + exception.getMessage();
        return new ResponseEntity<>(createErrorResponse(errorMessage, exception.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle the OrderNotFoundException class
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale);
        String errorMessage = message + SPACE_DELIMITER + exception.getMessage();
        return new ResponseEntity<>(createErrorResponse(errorMessage, exception.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    private ErrorResponse createErrorResponse(String errorMessage, int errorCode) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage);
        response.setErrorCode(errorCode);
        return response;
    }
}
