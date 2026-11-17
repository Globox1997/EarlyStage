package net.earlystage.mixin.client;

import net.earlystage.init.ConfigInit;
import net.earlystage.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.BlastFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.AbstractFurnaceRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.BlastFurnaceMenu;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(BlastFurnaceScreen.class)
public abstract class BlastFurnaceScreenMixin extends AbstractFurnaceScreen<BlastFurnaceMenu> {

    public BlastFurnaceScreenMixin(BlastFurnaceMenu handler, AbstractFurnaceRecipeBookComponent recipeBook, Inventory inventory, Component title, ResourceLocation background, ResourceLocation litProgressTexture, ResourceLocation burnProgressTexture) {
        super(handler, recipeBook, inventory, title, background, litProgressTexture, burnProgressTexture);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        super.renderBg(context, delta, mouseX, mouseY);
        if (ConfigInit.isBlastFurnaceExtraSlotEnabled()) {
            context.blit(RenderInit.GUI_ICON_TEXTURES, this.leftPos + 75, this.topPos + 16, 0, 0, 18, 18);
        }
    }

}
