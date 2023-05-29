package com.onezero.ozerp.appbase.enums;

public enum ResultStatus {
    SUCCESSFUL("Successful"),
    FAILED("Failed"),
    WARNING("Warning"),
    AWAITING("Awaiting");

    private final String label;

    ResultStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}