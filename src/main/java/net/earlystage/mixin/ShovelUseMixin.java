package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.earlystage.block.FlintBlock;
import net.earlystage.block.RockBlock;
import net.earlystage.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Item.class)
public class ShovelUseMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void useOnBlockMixin(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
        if (!context.getItemInHand().is(ItemTags.SHOVELS)) {
            return;
        }
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(BlockInit.ROCK) || blockState.is(BlockInit.FLINT)) {
            if (!world.isClientSide()) {
                if (blockState.is(BlockInit.ROCK)) {
                    ((RockBlock) blockState.getBlock()).cycleState(blockState, world, blockPos);
                } else {
                    ((FlintBlock) blockState.getBlock()).cycleState(blockState, world, blockPos);
                }
            }
            info.setReturnValue(InteractionResult.SUCCESS);
        }
    }

}
