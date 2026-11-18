package net.earlystage.mixin;

import net.earlystage.init.ConfigInit;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.RecipePropertySet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceMenu.class)
public abstract class AbstractFurnaceScreenHandlerMixin extends RecipeBookMenu {

    @Shadow
    @Mutable
    @Final
    private Container container;

    public AbstractFurnaceScreenHandlerMixin(MenuType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @ModifyConstant(method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;)V", constant = @Constant(intValue = 3))
    private static int initModifyMixin(int original, MenuType<?> type, ResourceKey<RecipePropertySet> allowedInputs, RecipeBookType category, int syncId, Inventory playerInventory) {
        if (type.equals(MenuType.BLAST_FURNACE) && ConfigInit.isBlastFurnaceExtraSlotEnabled()) {
            return 4;
        }
        return original;
    }

    @Inject(method = "<init>(Lnet/minecraft/world/inventory/MenuType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/inventory/RecipeBookType;ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/Container;Lnet/minecraft/world/inventory/ContainerData;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/AbstractFurnaceMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;", shift = Shift.AFTER, ordinal = 2))
    private void initTestMixin(CallbackInfo info) {
        if ((Object) this instanceof BlastFurnaceMenu && ConfigInit.isBlastFurnaceExtraSlotEnabled()) {
            this.addSlot(new Slot(this.container, 3, 76, 17));
        }
    }

}
