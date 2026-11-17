package net.earlystage.mixin;

import net.earlystage.init.ConfigInit;
import net.earlystage.misc.InputSlotFillerExtra;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlastFurnaceMenu.class)
public abstract class BlastFurnaceScreenHandlerMixin extends AbstractFurnaceMenu {

    public BlastFurnaceScreenHandlerMixin(MenuType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookType category, int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(type, recipeType, category, syncId, playerInventory, inventory, propertyDelegate);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        if (!ConfigInit.isBlastFurnaceExtraSlotEnabled()) {
            return super.quickMoveStack(player, slot);
        }
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2 != null && slot2.hasItem()) {
            ItemStack itemStack2 = slot2.getItem();
            itemStack = itemStack2.copy();

            if (slot == 2) {
                if (!this.moveItemStackTo(itemStack2, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                slot2.onQuickCraft(itemStack2, itemStack);
            } else if (slot == 1 || slot == 0 || slot == 3 ? !this.moveItemStackTo(itemStack2, 4, 40, false)
                    : (this.canSmelt(itemStack2) ? !this.moveItemStackTo(itemStack2, 0, 1, false)
                    : (this.isFuel(itemStack2) ? !this.moveItemStackTo(itemStack2, 1, 2, false)
                    : (slot >= 4 && slot < 31 ? !this.moveItemStackTo(itemStack2, 31, 40, false) : slot >= 31 && slot < 40 && !this.moveItemStackTo(itemStack2, 4, 31, false))))) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setByPlayer(ItemStack.EMPTY);
            } else {
                slot2.setChanged();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot2.onTake(player, itemStack2);
        }
        return itemStack;
    }

    @Override
    public void clearCraftingContent() {
        super.clearCraftingContent();
        if (ConfigInit.isBlastFurnaceExtraSlotEnabled()) {
            this.getSlot(3).set(ItemStack.EMPTY);
        }
    }

    @Override
    public int getGridWidth() {
        return ConfigInit.isBlastFurnaceExtraSlotEnabled() ? 2 : super.getGridWidth();
    }

    @Override
    public int getSize() {
        return ConfigInit.isBlastFurnaceExtraSlotEnabled() ? 4 : super.getSize();
    }

    @Override
    public void handlePlacement(boolean craftAll, RecipeHolder<?> recipe, ServerPlayer player) {
        if (ConfigInit.isBlastFurnaceExtraSlotEnabled()) {
            new InputSlotFillerExtra(this).recipeClicked(player, recipe, craftAll);
        } else {
            super.handlePlacement(craftAll, recipe, player);
        }
    }

}
