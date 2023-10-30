package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

@Mixin(BlastFurnaceBlockEntity.class)
public abstract class BlastFurnaceBlockEntityMixin extends AbstractFurnaceBlockEntity {

    public BlastFurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(blockEntityType, pos, state, recipeType);
    }

    @Inject(method = "Lnet/minecraft/block/entity/BlastFurnaceBlockEntity;<init>(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At("TAIL"))
    private void initMixin(BlockPos pos, BlockState state, CallbackInfo info) {
        this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

}
