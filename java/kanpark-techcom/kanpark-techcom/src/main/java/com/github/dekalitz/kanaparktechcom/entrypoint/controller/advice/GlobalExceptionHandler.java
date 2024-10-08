package com.github.dekalitz.kanaparktechcom.entrypoint.controller.advice;

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

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("failed");
        baseResponse.setErrors(errors);
        return new ResponseEntity<>(baseResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<BaseResponse> handleApplicationException(ApplicationException ex) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("failed");
        baseResponse.setErrors(Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(baseResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse> handlingInvalidMethod(HttpRequestMethodNotSupportedException ex) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus("failed");
        baseResponse.setErrors(Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(baseResponse, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}

