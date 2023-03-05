package net.earlystage.mixin.compat;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import me.ultrusmods.missingwilds.block.FallenLogBlock;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;

@Mixin(FallenLogBlock.class)
public class FallenLogBlockMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;<init>(Lnet/minecraft/block/AbstractBlock$Settings;)V"))
    private static AbstractBlock.Settings initMixin(AbstractBlock.Settings settings) {
        if (((AbstractBlockSettingsAccessor) settings).getMaterial() == Material.WOOD || ((AbstractBlockSettingsAccessor) settings).getMaterial() == Material.NETHER_WOOD) {
            return settings.requiresTool();
        }
        return settings;
    }

}
