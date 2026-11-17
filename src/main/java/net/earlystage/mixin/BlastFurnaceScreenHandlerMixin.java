package net.earlystage.mixin;

import net.earlystage.init.ConfigInit;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.earlystage.misc.InputSlotFillerExtra;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(BlastFurnaceMenu.class)
public abstract class BlastFurnaceScreenHandlerMixin extends AbstractFurnaceMenu {

    public BlastFurnaceScreenHandlerMixin(MenuType<?> type, ResourceKey<RecipePropertySet> allowedInputs, RecipeBookType category, int syncId, Inventory playerInventory, Container inventory,
            ContainerData propertyDelegate) {
        super(type, allowedInputs, category, syncId, playerInventory, inventory, propertyDelegate);
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

    @SuppressWarnings("unchecked")
    @Override
    public RecipeBookMenu.PostPlaceAction handlePlacement(boolean useMaxItems, boolean allowDroppingItemsToClear, RecipeHolder<?> recipe, ServerLevel level, Inventory inventory) {
        if (!ConfigInit.isBlastFurnaceExtraSlotEnabled()) {
            return super.handlePlacement(useMaxItems, allowDroppingItemsToClear, recipe, level, inventory);
        }
        Slot inputSlot = this.getSlot(0);
        Slot extraSlot = this.getSlot(3);
        List<Slot> slotsToClear = List.of(inputSlot, this.getSlot(2), extraSlot);
        if (recipe.value() instanceof ExtraBlastingRecipe) {
            return InputSlotFillerExtra.placeRecipe(this, 2, List.of(inputSlot, extraSlot), slotsToClear, (RecipeHolder<ExtraBlastingRecipe>) recipe,
                    extraRecipe -> extraRecipe.value().matches(inputSlot.getItem(), extraSlot.getItem()), useMaxItems, allowDroppingItemsToClear, inventory);
        }
        return InputSlotFillerExtra.placeRecipe(this, 1, List.of(inputSlot), slotsToClear, (RecipeHolder<AbstractCookingRecipe>) recipe,
                cookingRecipe -> cookingRecipe.value().matches(new SingleRecipeInput(inputSlot.getItem()), level), useMaxItems, allowDroppingItemsToClear, inventory);
    }

}
