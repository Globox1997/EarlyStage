package net.earlystage.compat;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.earlystage.data.CraftingRockRecipe;
import net.earlystage.init.BlockInit;
import net.earlystage.init.ConfigInit;
import net.earlystage.init.RenderInit;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;

public class CraftingRockJeiCategory implements IRecipeCategory<CraftingRockRecipe> {

    private final IGuiHelper guiHelper;
    private final IDrawable icon;
    private final ICraftingGridHelper craftingGridHelper;

    public CraftingRockJeiCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        this.icon = guiHelper.createDrawableItemLike(BlockInit.CRAFTING_ROCK);
        this.craftingGridHelper = guiHelper.createCraftingGridHelper();
    }

    @Override
    public IRecipeType<CraftingRockRecipe> getRecipeType() {
        return EarlyStageJeiPlugin.CRAFTING_ROCK;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("emi.category.earlystage.crafting_rock");
    }

    @Override
    public int getWidth() {
        return 116;
    }

    @Override
    public int getHeight() {
        return 54;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CraftingRockRecipe recipe, IFocusGroup focuses) {
        RecipeDisplay display = recipe.display();
        if (display instanceof ShapedCraftingRecipeDisplay shaped) {
            this.craftingGridHelper.createAndSetIngredientsFromDisplays(builder, shaped.ingredients(), shaped.width(), shaped.height());
        } else if (display instanceof ShapelessCraftingRecipeDisplay shapeless) {
            this.craftingGridHelper.createAndSetIngredientsFromDisplays(builder, shapeless.ingredients(), 0, 0);
            builder.setShapeless();
        }
        this.craftingGridHelper.createAndSetOutputs(builder, display.result());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, CraftingRockRecipe recipe, IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(61, 19);
        builder.addText(Component.translatable("emi.crafting_rock.info", ConfigInit.CONFIG.craftRockCraftHits), 56, 9).setPosition(60, 5);
        builder.addDrawableWidget(this.guiHelper.createDrawable(RenderInit.GUI_ICON_TEXTURES, 34, 0, 15, 12)).setPosition(65, 38)
                .setTooltip(Component.translatable("emi.crafting_rock.tooltip"));
    }

    @Override
    public Identifier getIdentifier(CraftingRockRecipe recipe) {
        return recipe.id();
    }

}
