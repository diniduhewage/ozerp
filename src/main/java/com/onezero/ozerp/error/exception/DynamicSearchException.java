package com.onezero.ozerp.error.exception;

public class DynamicSearchException extends AccessManagerRuntimeException {
    private static final long serialVersionUID = 1L;

    public DynamicSearchException() {
        super();
    }

    public DynamicSearchException(Exception e) {
        super(e);
    }

    public DynamicSearchException(String msg) {
        super(msg);
    }

    public DynamicSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
