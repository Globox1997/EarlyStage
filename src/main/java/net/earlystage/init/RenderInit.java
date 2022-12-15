package net.earlystage.init;

import net.earlystage.block.render.SieveBlockRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class RenderInit {

    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SIEVE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.REDSTONE_SIEVE, RenderLayer.getCutout());
        BlockEntityRendererRegistry.register(BlockInit.SIEVE_ENTITY, SieveBlockRenderer::new);
    }

}
