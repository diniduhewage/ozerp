package com.onenzero.ozerp.core.enums;

public enum AddressType {
    DELIVERY("Delivery"),
    INVOICE("Invoice"),
    VISIT("Visit"),
    PAY("Pay"),
    HOME("Home"),
    PRIMARY_CONTACT("Primary Contact"),
    SECONDARY_CONTACT("Secondary Contact");

    private final String label;
    AddressType(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
