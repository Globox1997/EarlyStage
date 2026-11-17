package net.earlystage.init;

import net.earlystage.EarlyStageMain;
import net.earlystage.block.render.CraftingRockBlockRenderer;
import net.earlystage.block.render.SieveBlockRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class RenderInit {

    public static final Identifier GUI_ICON_TEXTURES = EarlyStageMain.identifierOf("textures/gui/blast_furnace_extra_slot.png");
    public static final Identifier SIEVE = EarlyStageMain.identifierOf("textures/block/sieve_top.png");

    public static void init() {
        BlockEntityRendererRegistry.register(BlockInit.SIEVE_ENTITY, SieveBlockRenderer::new);
        BlockEntityRendererRegistry.register(BlockInit.CRAFTING_ROCK_ENTITY, CraftingRockBlockRenderer::new);
    }

}
