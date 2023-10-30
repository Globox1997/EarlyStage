package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

@Mixin(AbstractFurnaceScreenHandler.class)
public abstract class AbstractFurnaceScreenHandlerMixin extends AbstractRecipeScreenHandler<Inventory> {

    @Shadow
    @Mutable
    @Final
    private Inventory inventory;

    public AbstractFurnaceScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @ModifyConstant(method = "Lnet/minecraft/screen/AbstractFurnaceScreenHandler;<init>(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/recipe/book/RecipeBookCategory;ILnet/minecraft/entity/player/PlayerInventory;)V", constant = @Constant(intValue = 3))
    private static int initModifyMixin(int original, ScreenHandlerType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory category, int syncId,
            PlayerInventory playerInventory) {
        if (type.equals(ScreenHandlerType.BLAST_FURNACE)) {
            return 4;
        }
        return original;
    }

    // net/minecraft/screen/AbstractFurnaceScreenHandler.addSlot (Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;
    @Inject(method = "Lnet/minecraft/screen/AbstractFurnaceScreenHandler;<init>(Lnet/minecraft/screen/ScreenHandlerType;Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/recipe/book/RecipeBookCategory;ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/screen/PropertyDelegate;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/AbstractFurnaceScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;", shift = Shift.AFTER, ordinal = 2))
    private void initTestMixin(CallbackInfo info) {
        if ((Object) this instanceof BlastFurnaceScreenHandler) {
            this.addSlot(new Slot(this.inventory, 3, 76, 17));
        }
    }

}
