package net.earlystage.init;

import net.earlystage.EarlyStageMain;
import net.earlystage.block.render.CraftingRockBlockRenderer;
import net.earlystage.block.render.SieveBlockRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class RenderInit {

    public static final ResourceLocation GUI_ICON_TEXTURES = EarlyStageMain.identifierOf("textures/gui/blast_furnace_extra_slot.png");
    public static final ResourceLocation SIEVE = EarlyStageMain.identifierOf("textures/block/sieve_top.png");

    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SIEVE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.REDSTONE_SIEVE, RenderType.cutout());

        BlockEntityRenderers.register(BlockInit.SIEVE_ENTITY, SieveBlockRenderer::new);
        BlockEntityRenderers.register(BlockInit.CRAFTING_ROCK_ENTITY, CraftingRockBlockRenderer::new);
    }

}
