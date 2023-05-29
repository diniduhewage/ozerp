package com.onezero.ozerp.appbase.error.exception;


public class BadRequestException extends AccessManagerRuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super("Bad Request");
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
