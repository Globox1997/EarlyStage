package net.earlystage.mixin.client;

import java.util.List;

import net.earlystage.init.ConfigInit;
import net.earlystage.init.RenderInit;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.BlastFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlastFurnaceScreen.class)
public abstract class BlastFurnaceScreenMixin extends AbstractFurnaceScreen<BlastFurnaceMenu> {

    public BlastFurnaceScreenMixin(BlastFurnaceMenu menu, Inventory inventory, Component title, Component recipeFilterName, Identifier texture, Identifier litProgressSprite,
            Identifier burnProgressSprite, List<RecipeBookComponent.TabInfo> tabInfos) {
        super(menu, inventory, title, recipeFilterName, texture, litProgressSprite, burnProgressSprite, tabInfos);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractBackground(graphics, mouseX, mouseY, delta);
        if (ConfigInit.isBlastFurnaceExtraSlotEnabled()) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, RenderInit.GUI_ICON_TEXTURES, this.leftPos + 75, this.topPos + 16, 0.0f, 0.0f, 18, 18, 256, 256);
        }
    }

}
