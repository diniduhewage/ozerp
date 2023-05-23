package com.onezero.ozerp.enums;

public enum ContactType {
    HOME("Home"),
    MOBILE("Mobile"),
    WORK("Work"),
    EMAIL("Email"),
    WHATSAPP("Whatsapp");

    private String value;

    ContactType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
