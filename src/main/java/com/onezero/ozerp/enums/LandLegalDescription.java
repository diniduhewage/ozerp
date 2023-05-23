package com.onezero.ozerp.enums;

public enum LandLegalDescription {
    PRIVATE("Land that is privately owned by an individual or organization"),
    STATE("Land owned and managed by the government"),
    PERMIT("Land granted by the government under a permit system for specific purposes, usually agriculture"),
    LEASED("Land that is leased from the owner for a specified period and purpose"),
    MAHAWELI("Land within the Mahaweli Development scheme, managed by the Mahaweli Authority"),
    GIFTED("Land that has been gifted to an individual or organization"),
    INHERITED("Land that has been inherited from a family member"),
    DEED("Land with a registered deed, providing legal proof of ownership"),
    CUSTOMARY("Land under traditional usage rights, governed by customary practices");

    private final String landLegalDescription;

    LandLegalDescription(String description) {
        this.landLegalDescription = description;
    }

    public String getLandLegalDescription() {
        return landLegalDescription;
    }

    @Override
    public String toString() {
        return landLegalDescription;
    }
}
