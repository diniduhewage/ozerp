package com.onezero.ozerp.enums;

public enum RegionParentType {
    PROVINCIAL("Provincial"),
    INTER_PROVINCIAL("Inter Provincial");

    private final String label;

    RegionParentType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
