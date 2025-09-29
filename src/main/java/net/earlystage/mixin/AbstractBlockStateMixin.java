package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

    @Inject(method = "isToolRequired", at = @At("HEAD"), cancellable = true)
    private void earlystage$requireToolForLogs(CallbackInfoReturnable<Boolean> cir) {
        if (((BlockState)(Object)this).isIn(BlockTags.LOGS)) {
            cir.setReturnValue(true);
        }
    }
}
