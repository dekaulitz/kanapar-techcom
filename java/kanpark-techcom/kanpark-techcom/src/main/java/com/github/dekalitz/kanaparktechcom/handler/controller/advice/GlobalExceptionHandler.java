package com.github.dekalitz.kanaparktechcom.handler.controller.advice;

import com.github.dekalitz.kanaparktechcom.application.dto.BaseResponse;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        BaseResponse<Object> baseResponseRecord = new BaseResponse<>("failed", null, errors);
        return new ResponseEntity<>(baseResponseRecord, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<BaseResponse<Object>> handleApplicationException(ApplicationException ex) {
        BaseResponse<Object> baseResponseRecord = new BaseResponse<>("failed", null, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(baseResponseRecord, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<Object>> handlingInvalidMethod(HttpRequestMethodNotSupportedException ex) {
        BaseResponse<Object> baseResponseRecord = new BaseResponse<>("failed", null, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(baseResponseRecord, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}

