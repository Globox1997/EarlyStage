package net.earlystage.mixin;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.earlystage.init.RecipeInit;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.earlystage.misc.ExtraBlastingRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends BaseContainerBlockEntity {

    @Shadow
    int cookingProgress;
    @Shadow
    int cookingTotalTime;
    @Shadow
    @Mutable
    @Final
    private Object2IntOpenHashMap<ResourceLocation> recipesUsed;
    @Shadow
    protected NonNullList<ItemStack> items;

    public AbstractFurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @ModifyVariable(method = "serverTick", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;getMaxStackSize()I"))
    private static RecipeHolder<?> tickMixin(RecipeHolder<?> original, Level world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity) {
        if (blockEntity.getType().equals(BlockEntityType.BLAST_FURNACE) && !blockEntity.getItem(3).isEmpty()) {
            RecipeHolder<ExtraBlastingRecipe> extraBlastingRecipe = world.getRecipeManager()
                    .getRecipeFor(RecipeInit.EXTRA_BLASTING, new ExtraBlastingRecipeInput(blockEntity.getItem(0), blockEntity.getItem(3)), world).orElse(null);
            if (extraBlastingRecipe != null) {
                return extraBlastingRecipe;
            }
        }
        return original;
    }

    @Inject(method = "getTotalCookTime", at = @At("HEAD"), cancellable = true)
    private static void getCookTimeMixin(Level world, AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Integer> info) {
        if (furnace.getType().equals(BlockEntityType.BLAST_FURNACE)) {
            RecipeHolder<ExtraBlastingRecipe> recipe = world.getRecipeManager().getRecipeFor(RecipeInit.EXTRA_BLASTING, new ExtraBlastingRecipeInput(furnace.getItem(0), furnace.getItem(3)), world)
                    .orElse(null);
            if (recipe != null) {
                info.setReturnValue(recipe.value().getCookTime());
            }
        }
    }

    @Inject(method = "burn", at = @At("TAIL"))
    private static void craftRecipeMixin(RegistryAccess registryManager, @Nullable RecipeHolder<?> recipe, NonNullList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> info) {
        if (recipe != null && recipe.value() instanceof ExtraBlastingRecipe extraBlastingRecipe) {
            slots.get(3).shrink(extraBlastingRecipe.getExtraInputCount());
            if (extraBlastingRecipe.getInputCount() > 1) {
                slots.get(0).shrink(extraBlastingRecipe.getInputCount() - 1);
            }
        }
    }

    @Inject(method = "setItem", at = @At("HEAD"))
    private void setStackHeadMixin(int slot, ItemStack stack, CallbackInfo info, @Share("extraSlotChanged") LocalBooleanRef extraSlotChanged) {
        extraSlotChanged.set(this.getType().equals(BlockEntityType.BLAST_FURNACE) && slot == 3 && !stack.isEmpty() && !ItemStack.isSameItemSameComponents(this.items.get(slot), stack));
    }

    @Inject(method = "setItem", at = @At("TAIL"))
    private void setStackMixin(int slot, ItemStack stack, CallbackInfo info, @Share("extraSlotChanged") LocalBooleanRef extraSlotChanged) {
        if (extraSlotChanged.get()) {
            this.cookingTotalTime = getTotalCookTime(this.level, (AbstractFurnaceBlockEntity) (Object) this);
            this.cookingProgress = 0;
            this.setChanged();
        }
    }

    @Inject(method = "getRecipesToAwardAndPopExperience", at = @At("HEAD"), cancellable = true)
    private void getRecipesUsedAndDropExperienceMixin(ServerLevel world, Vec3 pos, CallbackInfoReturnable<List<RecipeHolder<?>>> info) {
        if (this.getType().equals(BlockEntityType.BLAST_FURNACE)) {
            ArrayList<RecipeHolder<?>> blastFurnaceExtraRecipes = Lists.newArrayList();
            for (Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
                world.getRecipeManager().byKey(entry.getKey()).ifPresent(recipe -> {
                    blastFurnaceExtraRecipes.add(recipe);
                    if (recipe.value() instanceof ExtraBlastingRecipe extraBlastingRecipe) {
                        createExperience(world, pos, entry.getIntValue(), extraBlastingRecipe.getExperience());
                    } else {
                        createExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe) recipe.value()).getExperience());
                    }
                });
            }
            info.setReturnValue(blastFurnaceExtraRecipes);
        }
    }

    @Shadow
    private static void createExperience(ServerLevel world, Vec3 pos, int multiplier, float experience) {
    }

    @Shadow
    private static int getTotalCookTime(Level world, AbstractFurnaceBlockEntity furnace) {
        return 0;
    }
}
