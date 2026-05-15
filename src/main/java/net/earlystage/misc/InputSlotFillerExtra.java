package net.earlystage.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.InputSlotFiller;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

@SuppressWarnings({"unchecked", "rawtypes"})
public class InputSlotFillerExtra extends InputSlotFiller {

    private RecipeEntry<?> recipe = null;

    public InputSlotFillerExtra(AbstractRecipeScreenHandler handler) {
        super(handler);
    }

    @Override
    public void fillInputSlots(ServerPlayerEntity entity, RecipeEntry recipe, boolean craftAll) {
        this.recipe = recipe;
        super.fillInputSlots(entity, recipe, craftAll);
    }

    @Override
    public void acceptAlignedInput(Integer integer, int slot, int amount, int gridX, int gridY) {
        if (slot == 1) {
            slot = 3;
        }
        super.acceptAlignedInput(integer, slot, amount, gridX, gridY);
    }

    @Override
    protected int fillInputSlot(Slot slot, ItemStack stack, int i) {
        int k;
        int j = this.inventory.indexOf(stack);
        if (j == -1) {
            return -1;
        }

        int requiredCount = 1;
        if (this.recipe != null) {
            requiredCount = this.recipe.value().getIngredients().get(slot.getIndex() == 0 ? 0 : 1).getMatchingStacks()[0].getCount();
        }
        int oldCount = stack.getCount();
        if (oldCount > requiredCount) {
            this.inventory.removeStack(i, requiredCount);
            k = i;
        } else {
            requiredCount = oldCount;
            this.inventory.removeStack(i);
            k = this.inventory.getStack(j).getCount();
        }
        if (slot.getStack().isEmpty()) {
            slot.setStackNoCallbacks(stack.copyWithCount(requiredCount));
        } else {
            slot.getStack().increment(requiredCount);
        }
        return i - k;
    }

}
