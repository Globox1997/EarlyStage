package net.earlystage.misc;

import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.world.inventory.RecipeBookMenu;

@SuppressWarnings({"unchecked", "rawtypes"})
public class InputSlotFillerExtra extends ServerPlaceRecipe {

    public InputSlotFillerExtra(RecipeBookMenu handler) {
        super(handler);
    }

    @Override
    public void addItemToSlot(Integer integer, int slot, int amount, int gridX, int gridY) {
        if (slot == 1) {
            slot = 3;
        }
        super.addItemToSlot(integer, slot, amount, gridX, gridY);
    }

}
