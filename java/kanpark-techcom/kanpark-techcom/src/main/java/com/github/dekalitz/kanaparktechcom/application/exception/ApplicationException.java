package com.github.dekalitz.kanaparktechcom.application.exception;

import com.github.dekalitz.kanaparktechcom.application.records.ErrorCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ApplicationException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    private ErrorCode errorCode;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(ErrorCode message) {
        super();
        this.errorCode = message;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    protected ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
