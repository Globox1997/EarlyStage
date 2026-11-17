package net.earlystage.compat;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.earlystage.EarlyStageMain;
import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.BlockInit;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class SieveJeiCategory implements IRecipeCategory<SieveDropTemplate> {

    private final IDrawable icon;
    private final int height;

    public SieveJeiCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemLike(BlockInit.SIEVE);
        int maxRows = 1;
        for (SieveDropTemplate template : EarlyStageMain.SIEVE_DROP_TEMPLATES) {
            maxRows = Math.max(maxRows, getRows(template));
        }
        this.height = 4 + maxRows * 18 + 4;
    }

    private static int getRows(SieveDropTemplate template) {
        return Math.max(1, (template.getBlockDrops().size() + 3) / 4);
    }

    @Override
    public IRecipeType<SieveDropTemplate> getRecipeType() {
        return EarlyStageJeiPlugin.SIEVE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("emi.category.earlystage.sieve");
    }

    @Override
    public int getWidth() {
        return 120;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SieveDropTemplate template, IFocusGroup focuses) {
        int extraY = (getRows(template) - 1) * 8;
        builder.addInputSlot(4, 7 + extraY).setStandardSlotBackground().add(template.getBlockItem());
        for (int i = 0; i < template.getBlockDrops().size(); i++) {
            int x = 49 + (i % 4) * 18;
            int y = 5 + (i / 4) * 18;
            float chance = Math.round(template.getDropChances().get(i) * 1000f) / 10f;
            builder.addOutputSlot(x, y).setStandardSlotBackground().add(new ItemStack(template.getBlockDrops().get(i)))
                    .addRichTooltipCallback((view, tooltip) -> tooltip.add(Component.translatable("emi.sieve.tooltip", chance)));
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, SieveDropTemplate template, IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(23, 6 + (getRows(template) - 1) * 8);
    }

}
