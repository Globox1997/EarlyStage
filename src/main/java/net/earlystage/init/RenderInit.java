package net.earlystage.init;

import net.earlystage.block.render.CraftingRockBlockRenderer;
import net.earlystage.block.render.SieveBlockRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RenderInit {

    public static final Identifier GUI_ICON_TEXTURES = new Identifier("earlystage", "textures/gui/blast_furnace_extra_slot.png");

    public static void init() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.SIEVE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockInit.REDSTONE_SIEVE, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(BlockInit.SIEVE_ENTITY, SieveBlockRenderer::new);
        BlockEntityRendererFactories.register(BlockInit.CRAFTING_ROCK_ENTITY, CraftingRockBlockRenderer::new);
    }

}
