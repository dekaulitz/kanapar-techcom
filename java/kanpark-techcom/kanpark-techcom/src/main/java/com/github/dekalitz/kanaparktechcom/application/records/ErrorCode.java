package com.github.dekalitz.kanaparktechcom.application.records;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public record ErrorCode(String statusCode, String message, Integer httpCode) {

    public static ErrorCode errorOnEmailAlreadyRegistered() {
        return new ErrorCode("000", "email already registered", 400);
    }

    public static ErrorCode errorOnRequiredField(List<String> messages) {
        return new ErrorCode("001", StringUtils.join(messages), 400);
    }

    public static ErrorCode errorOnTokenInvalid() {
        return new ErrorCode("X403", "token invalid", 403);
    }

    public static ErrorCode errorOnTokenInvalid(String errorMessage) {
        return new ErrorCode("X403", errorMessage, 403);
    }

    public static ErrorCode errorOnTokenExpired(String errorMessage) {
        return new ErrorCode("X401", errorMessage, 403);
    }

    public static ErrorCode errorOnForbiddenRequest(String errorMessage) {
        return new ErrorCode("X401", errorMessage, 403);
    }

    public static ErrorCode errorOnInvalidLogin(String errorMessage) {
        return new ErrorCode("X405", errorMessage, 403);
    }

    public static ErrorCode errorOnInvalidLogin() {
        return new ErrorCode("X405", "invalid username or password", 403);
    }

    public static ErrorCode errorOnGenerateToken(String message) {
        return new ErrorCode("X450", message, 403);
    }

    public static ErrorCode errorOnGenerateRefreshToken(String message) {
        return new ErrorCode("X450",message, 403);
    }

    public static ErrorCode errorOnInvalidMethod(String message) {
        return new ErrorCode("X455", message, 405);
    }
}
