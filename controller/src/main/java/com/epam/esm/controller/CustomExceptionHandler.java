package com.epam.esm.controller;

import com.epam.esm.entity.ErrorResponse;
import com.epam.esm.exception.CustomServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 * The type Exception controller.
 */
@RestControllerAdvice
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CustomExceptionHandler {

    private final MessageSource messageSource;

    private static final int INTERNAL_SERVER_ERROR_CODE = 100;
    private static final int BIND_EXCEPTION_ERROR_CODE = 102;
    private static final int CONSTRAINT_VIOLATION_ERROR_CODE = 101;
    private static final String INTERNAL_SERVER_CODE = "error.internalServerError";
    private static final String SPACE_DELIMITER = " ";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";

    /**
     * Handle internal server error
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleInternalServerError(RuntimeException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(INTERNAL_SERVER_CODE, new Object[]{}, locale);
        return new ResponseEntity<>(createErrorResponse(errorMessage, INTERNAL_SERVER_ERROR_CODE),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Handles the CustomServiceException class
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the response entity
     */
    @ExceptionHandler(CustomServiceException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(CustomServiceException exception, Locale locale) {
        String message = messageSource.getMessage(exception.getErrorMessage(), new Object[]{}, locale);
        String errorMessage = message + SPACE_DELIMITER + exception.getMessage();
        return new ResponseEntity<>(createErrorResponse(errorMessage, exception.getErrorCode()), HttpStatus.NOT_FOUND);
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
        Map<String, Object> message = new HashMap<>();
        message.put(ERROR_MESSAGE, exception.getBindingResult().getAllErrors()
                .stream()
                .collect(
                        Collectors.toMap(
                                error -> ((FieldError) error).getField(),
                                error -> messageSource.getMessage(error, locale),
                                (existing, replacement) -> existing
                        )
                )
        );
        message.put(ERROR_CODE, BIND_EXCEPTION_ERROR_CODE);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle constraint violation exception
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        Map<String, Object> message = new HashMap<>();
        message.put(ERROR_MESSAGE, exception.getConstraintViolations()
                .stream()
                .collect(
                        Collectors.toMap(
                                ConstraintViolation::getInvalidValue,
                                ConstraintViolation::getMessage,
                                (existing, replacement) -> existing
                        )
                ));
        message.put(ERROR_CODE, CONSTRAINT_VIOLATION_ERROR_CODE);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createErrorResponse(String errorMessage, int errorCode) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage);
        response.setErrorCode(errorCode);
        return response;
    }
}
