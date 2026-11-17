package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.earlystage.block.FlintBlock;
import net.earlystage.block.RockBlock;
import net.earlystage.init.BlockInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {

    @Inject(method = "useOn", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void useOnBlockMixin(UseOnContext context, CallbackInfoReturnable<InteractionResult> info, Level world, BlockPos blockPos, BlockState blockState) {
        if (blockState.is(BlockInit.ROCK) || blockState.is(BlockInit.FLINT)) {
            if (!world.isClientSide()) {
                if (blockState.is(BlockInit.ROCK)) {
                    ((RockBlock) blockState.getBlock()).cycleState(blockState, world, blockPos);
                } else {
                    ((FlintBlock) blockState.getBlock()).cycleState(blockState, world, blockPos);
                }
            }
            info.setReturnValue(InteractionResult.sidedSuccess(world.isClientSide()));
        }
    }

}
