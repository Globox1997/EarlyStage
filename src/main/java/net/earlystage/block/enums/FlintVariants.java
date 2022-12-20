package net.earlystage.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum FlintVariants implements StringIdentifiable {
    SMALL("small"), MEDIUM("medium");

    private final String name;

    private FlintVariants(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
