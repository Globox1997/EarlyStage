package net.earlystage.mixin.client;

import net.earlystage.init.ConfigInit;
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
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(BlastFurnaceScreen.class)
public abstract class BlastFurnaceScreenMixin extends AbstractFurnaceScreen<BlastFurnaceScreenHandler> {

    public BlastFurnaceScreenMixin(BlastFurnaceScreenHandler handler, AbstractFurnaceRecipeBookScreen recipeBook, PlayerInventory inventory, Text title, Identifier background, Identifier litProgressTexture, Identifier burnProgressTexture) {
        super(handler, recipeBook, inventory, title, background, litProgressTexture, burnProgressTexture);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
        if (ConfigInit.CONFIG.blastFurnaceExtraSlot) {
            context.drawTexture(RenderInit.GUI_ICON_TEXTURES, this.x + 75, this.y + 16, 0, 0, 18, 18);
        }
    }

}
