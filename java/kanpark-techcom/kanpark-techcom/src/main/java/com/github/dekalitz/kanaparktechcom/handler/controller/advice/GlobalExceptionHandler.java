package com.github.dekalitz.kanaparktechcom.handler.controller.advice;

import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.records.ResultRecord;
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
    public ResponseEntity<ResultRecord<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ResultRecord<Object> resultRecordRecord = new ResultRecord<>("400", "failed", null, errors);
        return new ResponseEntity<>(resultRecordRecord, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResultRecord<Object>> handleApplicationException(ApplicationException ex) {
        var errCode = ex.getErrorRecord();
        ResultRecord<Object> resultRecordRecord = new ResultRecord<>(errCode.statusCode(),errCode.status(), null,errCode.messages());
        return new ResponseEntity<>(resultRecordRecord, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResultRecord<Object>> handlingInvalidMethod(HttpRequestMethodNotSupportedException ex) {
        ResultRecord<Object> resultRecordRecord = new ResultRecord<>("400", "failed", null, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(resultRecordRecord, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}

