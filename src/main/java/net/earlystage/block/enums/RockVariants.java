package net.earlystage.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum RockVariants implements StringIdentifiable {
    SMALL("small"), MEDIUM("medium"), LARGE("large"), EXTRA_LARGE("extra_large");

    private final String name;

    private RockVariants(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
