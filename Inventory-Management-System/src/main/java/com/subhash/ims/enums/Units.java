package com.subhash.ims.enums;

public enum Units {
    KG("Kilogram"),
    GRAM("Gram"),
    LITER("Liter"),
    MILLILITER("Milliliter"),
    UNIT("Unit"); // generic count

    private final String displayName;

    Units(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
