package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import com.github.dekalitz.kanaparktechcom.application.records.ErrorRecord;
import jakarta.servlet.ServletException;
import lombok.Getter;

@Getter
public class UnauthorizedException extends ServletException {
    private ErrorRecord errorRecord;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(ErrorRecord errorRecord) {
        super();
        this.errorRecord = errorRecord;
    }
}
