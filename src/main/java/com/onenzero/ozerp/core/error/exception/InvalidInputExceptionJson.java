package com.onenzero.ozerp.core.error.exception;

public class InvalidInputExceptionJson extends Exception {

    private String errorMessage;

    public InvalidInputExceptionJson() {
        super();
    }

    public InvalidInputExceptionJson(String errorMessage, String internalMessage) {
        super(internalMessage);
        this.errorMessage = errorMessage;
    }

    public InvalidInputExceptionJson(String errorMessage, String internalMessage,
                                     Exception source) {
        super(internalMessage, source);
        this.errorMessage = errorMessage;
    }

    public InvalidInputExceptionJson(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}