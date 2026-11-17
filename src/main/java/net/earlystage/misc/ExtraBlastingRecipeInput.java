package net.earlystage.misc;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ExtraBlastingRecipeInput(ItemStack input, ItemStack extraInput) implements RecipeInput {

    @Override
    public ItemStack getItem(int slot) {
        return switch (slot) {
            case 0 -> this.input;
            case 1 -> this.extraInput;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return this.input.isEmpty() && this.extraInput.isEmpty();
    }
}
