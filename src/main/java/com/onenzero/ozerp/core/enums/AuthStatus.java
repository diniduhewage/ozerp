package com.onenzero.ozerp.core.enums;

public enum AuthStatus {
    NEW("New"),
    ACTIVE("Active");

    private final String label;

    AuthStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
