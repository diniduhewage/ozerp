package com.onenzero.ozerp.enterp.enums;

public enum ContactInfoType {
    PHONE("Phone"),
    EMAIL("Email"),
    WHATSAPP("Whats App"),
    OTHER("Other");

    private final String label;
    ContactInfoType(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
