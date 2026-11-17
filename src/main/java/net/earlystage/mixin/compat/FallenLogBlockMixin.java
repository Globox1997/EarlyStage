package net.earlystage.mixin.compat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import me.ultrusmods.missingwilds.block.FallenLogBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

@Mixin(FallenLogBlock.class)
public class FallenLogBlockMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;<init>(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V"))
    private static BlockBehaviour.Properties initMixin(BlockBehaviour.Properties settings) {
        return settings.requiresCorrectToolForDrops();
    }

}
