package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * The type Exception controller.
 */
@RestControllerAdvice
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

    private final MessageSource messageSource;

    private static final int INTERNAL_SERVER_ERROR_CODE = 100;
    private static final int BIND_EXCEPTION_ERROR_CODE = 102;
    private static final int CONSTRAINT_VIOLATION_ERROR_CODE = 101;
    private static final String INTERNAL_SERVER_CODE = "error.internalServerError";
    private static final String SPACE_DELIMITER = " ";
    private static final String ERROR_MESSAGE = "message";

    /**
     * Handle internal server error
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    /*@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleInternalServerError(RuntimeException exception, Locale locale) {
        Map<Object, String> message = new HashMap<>();
        message.put(ERROR_MESSAGE, messageSource.getMessage(INTERNAL_SERVER_CODE, new Object[]{}, locale));
        return new ResponseEntity<>(createCustomErrorResponse(message, INTERNAL_SERVER_ERROR_CODE),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }*/


    /**
     * Handles the CustomServiceException class
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(CustomServiceException exception, Locale locale) {
        Map<Object, String> message = new HashMap<>();
        message.put(ERROR_MESSAGE,
                messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale)
                        + SPACE_DELIMITER + exception.getMessage());
        return new ResponseEntity<>(createCustomErrorResponse(message, exception.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle method argument not valid exception
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindExceptionException(BindException exception, Locale locale) {
        return new ResponseEntity<>(createCustomErrorResponse(
                exception.getBindingResult().getAllErrors()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        error -> ((FieldError) error).getField(),
                                        error -> messageSource.getMessage(error, locale),
                                        (existing, replacement) -> existing
                                )
                        ), BIND_EXCEPTION_ERROR_CODE
        ), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle constraint violation exception
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>(createCustomErrorResponse(
                exception.getConstraintViolations()
                        .stream()
                        .collect(
                                Collectors.toMap(constraintViolation -> StreamSupport
                                                .stream(constraintViolation.getPropertyPath().spliterator(), false)
                                                .reduce((x, y) -> y).orElse(null),
                                        ConstraintViolation::getMessage,
                                        (existing, replacement) -> existing
                                )
                        ), CONSTRAINT_VIOLATION_ERROR_CODE
        ), HttpStatus.BAD_REQUEST);
    }

    private CustomErrorResponse createCustomErrorResponse(Map<Object, String> errorMessage, int errorCode) {
        return CustomErrorResponse.builder()
                .errorMessage(errorMessage)
                .errorCode(errorCode)
                .build();
    }
}
