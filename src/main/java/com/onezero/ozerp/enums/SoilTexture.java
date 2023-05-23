package com.onezero.ozerp.enums;

public enum SoilTexture {
    SANDY("Sandy"),
    LOAM("Loam"),
    CLAY("Clay");

    private final String soilTexture;


    private SoilTexture(String soilTexture) {
        this.soilTexture = soilTexture;
    }

    public String getSoilTexture() {
        return soilTexture;
    }

    @Override
    public String toString() {
        return soilTexture;
    }

}
