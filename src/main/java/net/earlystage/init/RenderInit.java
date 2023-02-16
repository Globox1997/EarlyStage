package net.earlystage.init;

import net.earlystage.block.render.CraftingRockBlockRenderer;
import net.earlystage.block.render.SieveBlockRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@Environment(EnvType.CLIENT)
public class RenderInit {

    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SIEVE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.REDSTONE_SIEVE, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(BlockInit.SIEVE_ENTITY, SieveBlockRenderer::new);
        BlockEntityRendererFactories.register(BlockInit.CRAFTING_ROCK_ENTITY, CraftingRockBlockRenderer::new);
    }

}
