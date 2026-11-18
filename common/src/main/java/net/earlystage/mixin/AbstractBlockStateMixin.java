package net.earlystage.mixin;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class AbstractBlockStateMixin {

    @Inject(method = "requiresCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
    private void isToolRequiredMixin(CallbackInfoReturnable<Boolean> info) {
        BlockState state = (BlockState) (Object) this;
        if (state.is(BlockTags.LOGS) && state.is(BlockTags.MINEABLE_WITH_AXE)) {
            info.setReturnValue(true);
        }
    }

}
