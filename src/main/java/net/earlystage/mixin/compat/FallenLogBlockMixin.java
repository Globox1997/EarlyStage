package net.earlystage.mixin.compat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import me.ultrusmods.missingwilds.block.FallenLogBlock;
import net.minecraft.block.AbstractBlock;

@Mixin(FallenLogBlock.class)
public class FallenLogBlockMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
    private static AbstractBlock.Settings initMixin(AbstractBlock.Settings settings) {
        return settings.requiresTool();
    }

}
