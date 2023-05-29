package com.onezero.ozerp.appbase.error.exception;

public class NotFoundException extends AccessManagerRuntimeException {
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super("Not Found");
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
