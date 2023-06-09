package com.onezero.ozerp.appbase.error.exception;

public class AccessDeniedException extends OzErpRuntimeException {

    private static final long serialVersionUID = 1L;

    public AccessDeniedException() {
        this("Forbidden");
    }

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
