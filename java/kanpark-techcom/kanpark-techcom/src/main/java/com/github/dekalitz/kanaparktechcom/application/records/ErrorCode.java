package com.github.dekalitz.kanaparktechcom.application.records;

import java.util.Collections;
import java.util.List;

public record ErrorCode(String statusCode, List<String> messages, Integer httpCode) {

    public static ErrorCode errorOnEmailAlreadyRegistered() {
        return new ErrorCode("000", Collections.singletonList("email already registered"), 400);
    }

    public static ErrorCode errorOnRequiredField(List<String> messages) {
        return new ErrorCode("001", messages, 400);
    }

    public static ErrorCode errorOnTokenInvalid() {
        return new ErrorCode("X403", Collections.singletonList("token invalid"), 403);
    }

    public static ErrorCode errorOnTokenInvalid(String errorMessage) {
        return new ErrorCode("X403", Collections.singletonList(errorMessage), 403);
    }

    public static ErrorCode errorOnForbiddenRequest(String errorMessage) {
        return new ErrorCode("X401", Collections.singletonList(errorMessage), 403);
    }

    public static ErrorCode errorOnInvalidLogin(String errorMessage) {
        return new ErrorCode("X405", Collections.singletonList(errorMessage), 403);
    }

    public static ErrorCode errorOnInvalidLogin() {
        return new ErrorCode("X405", Collections.singletonList("invalid username or password"), 403);
    }

    public static ErrorCode errorOnGenerateToken(String message) {
        return new ErrorCode("X450", Collections.singletonList(message), 403);
    }

    public static ErrorCode errorOnGenerateRefreshToken(String message) {
        return new ErrorCode("X450", Collections.singletonList(message), 403);
    }

    public static ErrorCode errorOnInvalidMethod(String message) {
        return new ErrorCode("X455", Collections.singletonList(message), 405);
    }
}
