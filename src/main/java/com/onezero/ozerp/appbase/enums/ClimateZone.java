package com.onezero.ozerp.appbase.enums;

public enum ClimateZone {
    DRY("DRY"), INTERMEDIATE("INTERMEDIATE"), WET("WET");

    private final String label;

    ClimateZone(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
