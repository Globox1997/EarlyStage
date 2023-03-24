package net.earlystage.mixin.compat;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ht.treechop.common.chop.ChopResult;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(ChopResult.class)
public class ChopResultMixin {

    @Shadow
    @Mutable
    @Final
    private World level;

    @Inject(method = "Lht/treechop/common/chop/ChopResult;apply(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/item/ItemStack;Z)Z", at = @At("HEAD"), cancellable = true)
    private void applyMixin(BlockPos targetPos, ServerPlayerEntity player, ItemStack tool, boolean breakLeaves, CallbackInfoReturnable<Boolean> info) {
        if (player != null && !(tool.getItem() instanceof AxeItem)) {
            info.setReturnValue(false);
        }
    }
}
