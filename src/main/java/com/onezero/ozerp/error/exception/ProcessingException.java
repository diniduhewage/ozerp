package com.onezero.ozerp.error.exception;

public class ProcessingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProcessingException() {
        super();
    }

    public ProcessingException(Exception e) {
        super(e);
    }

    public ProcessingException(String msg) {
        super(msg);
    }

    public ProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
