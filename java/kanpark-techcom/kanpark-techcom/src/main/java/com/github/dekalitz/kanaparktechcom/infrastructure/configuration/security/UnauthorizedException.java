package com.github.dekalitz.kanaparktechcom.infrastructure.configuration.security;

import com.github.dekalitz.kanaparktechcom.application.records.ErrorCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;

@Getter
public class UnauthorizedException extends InsufficientAuthenticationException {
    private ErrorCode errorCode;

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(StringUtils.join(errorCode.message()));
        this.errorCode = errorCode;
    }
}
