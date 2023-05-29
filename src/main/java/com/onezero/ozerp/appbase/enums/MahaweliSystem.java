package com.onezero.ozerp.appbase.enums;

public enum MahaweliSystem {
    B("B"),
    C("C"),
    D("D"),
    G("G"),
    H("H"),
    L("L"),
    HURULUWEWA("Huruluwewa"),
    UDAWALAWE("Udawalawa"),
    E("E"),
    RAMBAKENOYA("Rambakenoya");

    private final String mahaweliSystem;


    private MahaweliSystem(String mahaweliSystem) {
        this.mahaweliSystem = mahaweliSystem;
    }

    public String getMahaweliSystem() {
        return mahaweliSystem;
    }

    @Override
    public String toString() {
        return mahaweliSystem;
    }

}

