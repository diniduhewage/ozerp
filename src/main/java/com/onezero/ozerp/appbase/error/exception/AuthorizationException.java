package com.onezero.ozerp.appbase.error.exception;

public class AuthorizationException extends OzErpRuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthorizationException() {
        this("Unauthorized");
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
