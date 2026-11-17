package net.earlystage.block.enums;

import net.minecraft.util.StringRepresentable;

public enum FlintVariants implements StringRepresentable {
    SMALL("small"), MEDIUM("medium");

    private final String name;

    private FlintVariants(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
