package com.onezero.ozerp.appbase.enums;

public enum InstitutionType {
    DEPARTMENT("Department"), DIVISION("Division"), UNIT("Unit"), LAB("Lab"), CORPORATION("Corporation");

    private final String value;

    private InstitutionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
