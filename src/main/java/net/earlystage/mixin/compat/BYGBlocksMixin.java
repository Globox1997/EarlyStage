package net.earlystage.mixin.compat;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import potionstudios.byg.common.block.BYGBlocks;
import potionstudios.byg.reg.BlockRegistryObject;

@Mixin(BYGBlocks.class)
public class BYGBlocksMixin {

    @Inject(method = "createLog", at = @At("HEAD"), cancellable = true, remap = false)
    private static void createLogMixin(String id, CallbackInfoReturnable<BlockRegistryObject<Block>> info) {
        info.setReturnValue(createBlock(() -> new PillarBlock(AbstractBlock.Settings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).requiresTool().strength(2.0f)), id));
    }

    @Shadow(remap = false)
    public static <B extends Block> BlockRegistryObject<B> createBlock(Supplier<? extends B> block, String id) {
        return null;
    }
}
