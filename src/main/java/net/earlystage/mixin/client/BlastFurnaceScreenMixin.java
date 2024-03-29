package net.earlystage.mixin.client;

import org.spongepowered.asm.mixin.Mixin;

import net.earlystage.init.RenderInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.ingame.BlastFurnaceScreen;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@Mixin(BlastFurnaceScreen.class)
public abstract class BlastFurnaceScreenMixin extends AbstractFurnaceScreen<BlastFurnaceScreenHandler> {

    public BlastFurnaceScreenMixin(BlastFurnaceScreenHandler handler, AbstractFurnaceRecipeBookScreen recipeBook, PlayerInventory inventory, Text title, Identifier background) {
        super(handler, recipeBook, inventory, title, background);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
        context.drawTexture(RenderInit.GUI_ICON_TEXTURES, this.x + 75, this.y + 16, 0, 0, 18, 18);
    }

}
