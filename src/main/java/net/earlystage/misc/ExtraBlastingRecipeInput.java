package net.earlystage.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record ExtraBlastingRecipeInput(ItemStack input, ItemStack extraInput) implements RecipeInput {

    @Override
    public ItemStack getStackInSlot(int slot) {
        return switch (slot) {
            case 0 -> this.input;
            case 1 -> this.extraInput;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int getSize() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        return this.input.isEmpty() && this.extraInput.isEmpty();
    }
}
