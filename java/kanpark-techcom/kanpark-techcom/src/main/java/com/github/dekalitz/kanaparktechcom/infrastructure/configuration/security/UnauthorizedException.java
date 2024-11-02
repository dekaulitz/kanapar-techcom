package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import jakarta.servlet.ServletException;

public class UnauthorizedException extends ServletException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
