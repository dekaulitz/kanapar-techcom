package com.github.dekalitz.kanaparktechcom.application.records;

import java.util.Collections;
import java.util.List;

public record ErrorRecord(String statusCode, String status, List<String> messages) {

    public static ErrorRecord EMAIL_ALREADY_REGISTERED(String email) {
        return new ErrorRecord("000", "success", Collections.singletonList(email + " already registered"));
    }

    public static ErrorRecord TOKEN_INVALID() {
        return new ErrorRecord("403", "failed", Collections.singletonList("token invalid"));
    }

    public static ErrorRecord TOKEN_INVALID(String errorMessage) {
        return new ErrorRecord("403", "failed", Collections.singletonList(errorMessage));
    }
}
