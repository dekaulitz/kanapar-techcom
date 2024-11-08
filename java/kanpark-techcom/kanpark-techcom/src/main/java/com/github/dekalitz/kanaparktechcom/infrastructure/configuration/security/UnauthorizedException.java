package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import com.github.dekalitz.kanaparktechcom.application.records.ErrorCode;
import jakarta.servlet.ServletException;
import lombok.Getter;

@Getter
public class UnauthorizedException extends ServletException {
    private ErrorCode errorCode;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
