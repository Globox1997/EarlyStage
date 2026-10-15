package net.earlystage.misc;

import net.minecraft.recipe.InputSlotFiller;
import net.minecraft.screen.AbstractRecipeScreenHandler;

@SuppressWarnings({"unchecked", "rawtypes"})
public class InputSlotFillerExtra extends InputSlotFiller {

    public InputSlotFillerExtra(AbstractRecipeScreenHandler handler) {
        super(handler);
    }

    @Override
    public void acceptAlignedInput(Integer integer, int slot, int amount, int gridX, int gridY) {
        if (slot == 1) {
            slot = 3;
        }
        super.acceptAlignedInput(integer, slot, amount, gridX, gridY);
    }

}
