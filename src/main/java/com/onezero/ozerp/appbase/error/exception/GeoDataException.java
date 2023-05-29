package com.onezero.ozerp.appbase.error.exception;

public class GeoDataException extends Exception {

    public GeoDataException() {
        super();
    }

    public GeoDataException(String message) {
        super(message);
    }

    public GeoDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeoDataException(Throwable cause) {
        super(cause);
    }

    protected GeoDataException(String message, Throwable cause, boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
