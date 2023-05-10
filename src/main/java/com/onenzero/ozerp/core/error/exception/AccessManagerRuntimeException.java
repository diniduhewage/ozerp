package com.onenzero.ozerp.core.error.exception;

public class AccessManagerRuntimeException extends RuntimeException {

    public AccessManagerRuntimeException() {
    }

    public AccessManagerRuntimeException(String message) {
        super(message);
    }

    public AccessManagerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessManagerRuntimeException(Throwable cause) {
        super(cause);
    }

    protected AccessManagerRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
