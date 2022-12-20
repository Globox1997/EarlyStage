package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.earlystage.block.FlintBlock;
import net.earlystage.block.RockBlock;
import net.earlystage.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/World;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", ordinal = 0), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void useOnBlockMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info, World world, BlockPos blockPos, BlockState blockState) {
        if (blockState.isOf(BlockInit.ROCK) || blockState.isOf(BlockInit.FLINT)) {
            if (!world.isClient) {
                if (blockState.isOf(BlockInit.ROCK))
                    ((RockBlock) blockState.getBlock()).cycleState(blockState, world, blockPos);
                else
                    ((FlintBlock) blockState.getBlock()).cycleState(blockState, world, blockPos);
            }
            info.setReturnValue(ActionResult.success(world.isClient));
        }
    }

}
