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

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.earlystage.init.RecipeInit;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends LockableContainerBlockEntity {

    @Shadow
    int cookTime;
    @Shadow
    int cookTimeTotal;
    @Shadow
    @Mutable
    @Final
    private Object2IntOpenHashMap<Identifier> recipesUsed;

    public AbstractFurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @ModifyVariable(method = "tick", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/block/entity/AbstractFurnaceBlockEntity;getMaxCountPerStack()I"))
    private static Recipe<?> tickMixin(Recipe<?> original, World world, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity) {
        if (blockEntity.getType().equals(BlockEntityType.BLAST_FURNACE) && !blockEntity.getStack(3).isEmpty()) {
            ExtraBlastingRecipe extraBlastingRecipe = world.getRecipeManager().getFirstMatch(RecipeInit.EXTRA_BLASTING, blockEntity, world).orElse(null);
            return extraBlastingRecipe;
        }
        return original;
    }

    @Inject(method = "getCookTime", at = @At("HEAD"), cancellable = true)
    private static void getCookTimeMixin(World world, AbstractFurnaceBlockEntity furnace, CallbackInfoReturnable<Integer> info) {
        if (furnace.getType().equals(BlockEntityType.BLAST_FURNACE)) {
            ExtraBlastingRecipe recipe = world.getRecipeManager().getFirstMatch(RecipeInit.EXTRA_BLASTING, furnace, world).orElse(null);
            if (recipe != null) {
                info.setReturnValue(recipe.getCookTime());
            }
        }
    }

    @Inject(method = "craftRecipe", at = @At("TAIL"))
    private static void craftRecipeMixin(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count, CallbackInfoReturnable<Boolean> info) {
        if (recipe != null && recipe.getType().equals(RecipeInit.EXTRA_BLASTING)) {
            slots.get(3).decrement(recipe.getIngredients().get(1).getMatchingStacks()[0].getCount());
            if (recipe.getIngredients().get(0).getMatchingStacks()[0].getCount() > 1) {
                slots.get(0).decrement(recipe.getIngredients().get(0).getMatchingStacks()[0].getCount() - 1);
            }
        }
    }

    @Inject(method = "setStack", at = @At("TAIL"))
    private void setStackMixin(int slot, ItemStack stack, CallbackInfo info) {
        if (this.getType().equals(BlockEntityType.BLAST_FURNACE) && slot == 3) {
            this.cookTimeTotal = getCookTime(this.world, (AbstractFurnaceBlockEntity) (Object) this);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Inject(method = "getRecipesUsedAndDropExperience", at = @At("HEAD"), cancellable = true)
    private void getRecipesUsedAndDropExperienceMixin(ServerWorld world, Vec3d pos, CallbackInfoReturnable<List<Recipe<?>>> info) {
        if (this.getType().equals(BlockEntityType.BLAST_FURNACE)) {
            ArrayList<Recipe<?>> blastFurnaceExtraRecipes = Lists.newArrayList();
            for (Object2IntMap.Entry<Identifier> entry : this.recipesUsed.object2IntEntrySet()) {
                world.getRecipeManager().get(entry.getKey()).ifPresent(recipe -> {
                    blastFurnaceExtraRecipes.add(recipe);
                    if (recipe instanceof ExtraBlastingRecipe) {
                        dropExperience(world, pos, entry.getIntValue(), ((ExtraBlastingRecipe) recipe).getExperience());
                    } else {
                        dropExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe) recipe).getExperience());
                    }
                });
            }
            info.setReturnValue(blastFurnaceExtraRecipes);
        }
    }

    @Shadow
    private static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
    }

    @Shadow
    private static int getCookTime(World world, AbstractFurnaceBlockEntity furnace) {
        return 0;
    }
}
