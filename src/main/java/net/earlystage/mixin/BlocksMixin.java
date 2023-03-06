package net.earlystage.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

@Mixin(Blocks.class)
public class BlocksMixin {

    @Inject(method = "createLogBlock", at = @At("HEAD"), cancellable = true)
    private static void createLogBlockMixin(MapColor topMapColor, MapColor sideMapColor, CallbackInfoReturnable<PillarBlock> info) {
        info.setReturnValue(new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).requiresTool()
                .strength(2.0f).sounds(BlockSoundGroup.WOOD)));
    }
}
