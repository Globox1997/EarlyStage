package net.earlystage.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;

import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends BaseContainerBlockEntity {

    @Shadow
    private int cookingTimer;
    @Shadow
    private int cookingTotalTime;
    @Shadow
    protected NonNullList<ItemStack> items;

    public AbstractFurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @ModifyExpressionValue(method = { "serverTick", "getTotalCookTime(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;)I" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/crafting/RecipeManager$CachedCheck;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeInput;Lnet/minecraft/server/level/ServerLevel;)Ljava/util/Optional;"))
    private static Optional<?> getRecipeForMixin(Optional<?> original, @Local(argsOnly = true) ServerLevel level, @Local(argsOnly = true) AbstractFurnaceBlockEntity blockEntity) {
        if (blockEntity.getType().equals(BlockEntityTypes.BLAST_FURNACE) && blockEntity.getContainerSize() > 3 && !blockEntity.getItem(3).isEmpty()) {
            Optional<RecipeHolder<ExtraBlastingRecipe>> extraBlastingRecipe = ExtraBlastingRecipe.getRecipeFor(level, blockEntity.getItem(0), blockEntity.getItem(3));
            if (extraBlastingRecipe.isPresent()) {
                return extraBlastingRecipe;
            }
        }
        return original;
    }

    @Inject(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;burn(Lnet/minecraft/core/NonNullList;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.AFTER))
    private static void burnMixin(ServerLevel level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo info, @Local RecipeHolder<?> recipe) {
        if (recipe != null && recipe.value() instanceof ExtraBlastingRecipe extraBlastingRecipe) {
            NonNullList<ItemStack> items = ((AbstractFurnaceBlockEntityMixin) (Object) blockEntity).items;
            items.get(3).shrink(extraBlastingRecipe.getExtraInputCount());
            if (extraBlastingRecipe.getInputCount() > 1) {
                items.get(0).shrink(extraBlastingRecipe.getInputCount() - 1);
            }
        }
    }

    @Inject(method = "setItem(ILnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"))
    private void setStackHeadMixin(int slot, ItemStack stack, CallbackInfo info, @Share("extraSlotChanged") LocalBooleanRef extraSlotChanged) {
        extraSlotChanged.set(this.getType().equals(BlockEntityTypes.BLAST_FURNACE) && slot == 3 && !stack.isEmpty() && !ItemStack.isSameItemSameComponents(this.items.get(slot), stack));
    }

    @Inject(method = "setItem(ILnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    private void setStackMixin(int slot, ItemStack stack, CallbackInfo info, @Share("extraSlotChanged") LocalBooleanRef extraSlotChanged) {
        if (extraSlotChanged.get() && this.level instanceof ServerLevel serverLevel) {
            this.cookingTotalTime = getTotalCookTime(serverLevel, (AbstractFurnaceBlockEntity) (Object) this);
            this.cookingTimer = 0;
            this.setChanged();
        }
    }

    @Shadow
    private static int getTotalCookTime(ServerLevel level, AbstractFurnaceBlockEntity entity) {
        return 0;
    }
}
