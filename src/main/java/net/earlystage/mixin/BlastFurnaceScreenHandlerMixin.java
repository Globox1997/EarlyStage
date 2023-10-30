package net.earlystage.mixin;

import java.util.Iterator;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.InputSlotFiller;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(BlastFurnaceScreenHandler.class)
public abstract class BlastFurnaceScreenHandlerMixin extends AbstractFurnaceScreenHandler {

    public BlastFurnaceScreenHandlerMixin(ScreenHandlerType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory category, int syncId, PlayerInventory playerInventory,
            Inventory inventory, PropertyDelegate propertyDelegate) {
        super(type, recipeType, category, syncId, playerInventory, inventory, propertyDelegate);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = (Slot) this.slots.get(slot);
        if (slot2 != null && slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();

            if (slot == 2) {
                if (!this.insertItem(itemStack2, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (slot == 1 || slot == 0 || slot == 3 ? !this.insertItem(itemStack2, 4, 40, false)
                    : (this.isSmeltable(itemStack2) ? !this.insertItem(itemStack2, 0, 1, false)
                            : (this.isFuel(itemStack2) ? !this.insertItem(itemStack2, 1, 2, false)
                                    : (slot >= 4 && slot < 31 ? !this.insertItem(itemStack2, 31, 40, false) : slot >= 31 && slot < 40 && !this.insertItem(itemStack2, 4, 31, false))))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot2.onTakeItem(player, itemStack2);
        }
        return itemStack;
    }

    @Override
    public void clearCraftingSlots() {
        super.clearCraftingSlots();
        this.getSlot(3).setStackNoCallbacks(ItemStack.EMPTY);
    }

    @Override
    public int getCraftingWidth() {
        return 2;
    }

    @Override
    public int getCraftingSlotCount() {
        return 4;
    }

    @Override
    public void fillInputSlots(boolean craftAll, Recipe<?> recipe, ServerPlayerEntity player) {
        new InputSlotFillerExtra(this).fillInputSlots(player, recipe, craftAll);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static class InputSlotFillerExtra extends InputSlotFiller {

        private Recipe<?> recipe = null;

        public InputSlotFillerExtra(AbstractRecipeScreenHandler handler) {
            super(handler);
        }

        @Override
        public void fillInputSlots(ServerPlayerEntity entity, Recipe recipe, boolean craftAll) {
            this.recipe = recipe;
            // Stop filling if not enough material is given (when extra count is set at recipe) - not easy
            // if (recipe.getType().equals(RecipeInit.EXTRA_BLASTING)) {
            // return;
            // }
            super.fillInputSlots(entity, recipe, craftAll);
        }

        @Override
        public void acceptAlignedInput(Iterator inputs, int slot, int amount, int gridX, int gridY) {
            if (slot == 1) {
                slot = 3;
            }
            super.acceptAlignedInput(inputs, slot, amount, gridX, gridY);
        }

        @Override
        protected void fillInputSlot(Slot slot, ItemStack stack) {
            int i = this.inventory.indexOf(stack);
            if (i == -1) {
                return;
            }
            ItemStack itemStack = this.inventory.getStack(i);
            if (itemStack.isEmpty()) {
                return;
            }
            int requiredCount = 1;
            if (this.recipe != null) {
                requiredCount = this.recipe.getIngredients().get(slot.getIndex() == 0 ? 0 : 1).getMatchingStacks()[0].getCount();
            }
            int oldCount = itemStack.getCount();
            if (oldCount > requiredCount) {
                this.inventory.removeStack(i, requiredCount);
            } else {
                requiredCount = oldCount;
                this.inventory.removeStack(i);
            }
            if (slot.getStack().isEmpty()) {
                slot.setStackNoCallbacks(itemStack.copyWithCount(requiredCount));
            } else {
                slot.getStack().increment(requiredCount);
            }
        }

    }

}
