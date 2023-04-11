package com.khmil.management.enums;

public enum DeckType {
    STANDARD("Standard"),
    T_SHIRT("T-Shirt"),
    FIBONACCI("Fibonacci");

    private final String displayName;

    DeckType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
