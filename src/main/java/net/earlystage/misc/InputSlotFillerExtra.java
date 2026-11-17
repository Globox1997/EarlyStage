package net.earlystage.misc;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;

public class InputSlotFillerExtra {

    public static <I extends RecipeInput, R extends Recipe<I>> RecipeBookMenu.PostPlaceAction placeRecipe(RecipeBookMenu menu, int gridWidth, List<Slot> gridSlots, List<Slot> slotsToClear,
            RecipeHolder<R> recipe, Predicate<RecipeHolder<R>> recipeMatches, boolean useMaxItems, boolean allowDroppingItemsToClear, Inventory inventory) {
        return ServerPlaceRecipe.placeRecipe(new ServerPlaceRecipe.CraftingMenuAccess<R>() {
            @Override
            public void fillCraftSlotsStackedContents(StackedItemContents stackedContents) {
                menu.fillCraftSlotsStackedContents(stackedContents);
            }

            @Override
            public void clearCraftingContent() {
                slotsToClear.forEach(slot -> slot.set(ItemStack.EMPTY));
            }

            @Override
            public boolean recipeMatches(RecipeHolder<R> recipe) {
                return recipeMatches.test(recipe);
            }
        }, gridWidth, 1, gridSlots, slotsToClear, inventory, recipe, useMaxItems, allowDroppingItemsToClear);
    }

}
