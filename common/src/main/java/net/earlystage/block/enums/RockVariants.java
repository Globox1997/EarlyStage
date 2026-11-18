package net.earlystage.block.enums;

import net.minecraft.util.StringRepresentable;

public enum RockVariants implements StringRepresentable {
    SMALL("small"), MEDIUM("medium"), LARGE("large"), EXTRA_LARGE("extra_large");

    private final String name;

    private RockVariants(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
