package com.epam.esm.controller;

import com.epam.esm.exception.CertificateNotFoundException;
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
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private static int CERTIFICATE_NOT_FOUND_ERROR_CODE = 103;
    private static int TAG_NOT_FOUND_ERROR_CODE = 104;

    @ExceptionHandler(CertificateNotFoundException.class)
    public ResponseEntity<Object> handleControllerNotFoundException(CertificateNotFoundException exception, WebRequest request) {
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("errorMessage",exception.getMessage());
        body.put("errorCode",CERTIFICATE_NOT_FOUND_ERROR_CODE);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<Object> handleTagNotFoundException(TagNotFoundException exception, WebRequest request) {
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("errorMessage",exception.getMessage());
        body.put("errorCode",TAG_NOT_FOUND_ERROR_CODE);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
