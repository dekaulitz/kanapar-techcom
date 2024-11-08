package com.github.dekalitz.kanaparktechcom.handler.controller.advice;

import com.github.dekalitz.kanaparktechcom.application.dto.Response;
import com.github.dekalitz.kanaparktechcom.application.exception.ApplicationException;
import com.github.dekalitz.kanaparktechcom.application.records.ErrorCode;
import com.github.dekalitz.kanaparktechcom.application.records.ResultRecord;
import com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security.UnauthorizedException;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        var errCode = ErrorCode.errorOnRequiredField(errors);
        var result = new Response<>(new Response.MetaResponse("failed"), null,
                new Response.ErrorResponse(errCode.statusCode(), Strings.join(errCode.messages(), ',')));
        var httpCode = HttpStatus.resolve(errCode.httpCode());
        return new ResponseEntity<>(result, new HttpHeaders(), httpCode != null ? httpCode : HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Response<Object>> handleApplicationException(ApplicationException ex) {
        var errCode = ex.getErrorCode();
        var result = new Response<>(new Response.MetaResponse("failed"), null,
                new Response.ErrorResponse(errCode.statusCode(), Strings.join(errCode.messages(), ',')));
        var httpCode = HttpStatus.resolve(errCode.httpCode());
        return new ResponseEntity<>(result, new HttpHeaders(), httpCode != null ? httpCode : HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<Response<Object>> handleApplicationException(ServletException ex) {
        if (ex instanceof UnauthorizedException) {
            var errCode = ((UnauthorizedException) ex).getErrorCode();
            var result = new Response<>(new Response.MetaResponse("failed"), null,
                    new Response.ErrorResponse(errCode.statusCode(), Strings.join(errCode.messages(), ',')));
            var httpCode = HttpStatus.resolve(errCode.httpCode());
            return new ResponseEntity<>(result, new HttpHeaders(), httpCode != null ? httpCode : HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Response<Object>> handleAccessDeniedException(AuthenticationException ex) {
        var errCode = ErrorCode.errorOnForbiddenRequest(ex.getMessage());
        var result = new Response<>(new Response.MetaResponse("failed"), null,
                new Response.ErrorResponse(errCode.statusCode(), Strings.join(errCode.messages(), ',')));
        var httpCode = HttpStatus.resolve(errCode.httpCode());
        return new ResponseEntity<>(result, new HttpHeaders(), httpCode != null ? httpCode : HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResultRecord<Object>> handlingInvalidMethod(HttpRequestMethodNotSupportedException ex) {
        ResultRecord<Object> resultRecordRecord = new ResultRecord<>("400", "failed", null, Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(resultRecordRecord, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}

