package com.github.dekalitz.kanaparktechcom.application.exception;

import com.github.dekalitz.kanaparktechcom.application.records.ErrorRecord;
import lombok.Getter;

@Getter
public class ApplicationException extends Exception {
    private ErrorRecord errorRecord;

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(ErrorRecord message) {
        super();
        this.errorRecord = message;
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
