package com.epam.esm.controller;

import com.epam.esm.exception.CertificateNotFoundException;
import com.epam.esm.exception.InvalidDataFormException;
import com.epam.esm.exception.TagNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class ExceptionController extends ResponseEntityExceptionHandler {

    private static final int TAG_NOT_FOUND_ERROR_CODE = 104;
    private static final String ERROR_MESSAGE_TITLE = "errorMessage";
    private static final String ERROR_CODE_TITLE = "errorCode";

    @ExceptionHandler(CertificateNotFoundException.class)
    public ResponseEntity<Object> handleControllerNotFoundException(CertificateNotFoundException exception, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_MESSAGE_TITLE, exception.getMessage());
        body.put(ERROR_CODE_TITLE, CertificateNotFoundException.CERTIFICATE_NOT_FOUND_ERROR_CODE);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Object> handleTagNotFoundException(TagNotFoundException exception, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_MESSAGE_TITLE, exception.getMessage());
        body.put(ERROR_CODE_TITLE, TAG_NOT_FOUND_ERROR_CODE);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataFormException.class)
    public ResponseEntity<Object> handleInvalidDataFormException(InvalidDataFormException exception, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_MESSAGE_TITLE, exception.getMessage());
        body.put(ERROR_CODE_TITLE, InvalidDataFormException.INVALID_DATA_FROM_ERROR_CODE);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
