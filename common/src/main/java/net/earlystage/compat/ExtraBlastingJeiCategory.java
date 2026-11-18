package net.earlystage.compat;

import java.util.List;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ExtraBlastingJeiCategory implements IRecipeCategory<RecipeHolder<ExtraBlastingRecipe>> {

    private final IDrawable icon;

    public ExtraBlastingJeiCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemLike(Items.BLAST_FURNACE);
    }

    @Override
    public IRecipeType<RecipeHolder<ExtraBlastingRecipe>> getRecipeType() {
        return EarlyStageJeiPlugin.EXTRA_BLASTING;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.jei.category.blasting");
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 39;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    private static List<ItemStack> stacks(Ingredient ingredient, int count) {
        return ingredient.items().map(holder -> new ItemStack(holder, count)).toList();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ExtraBlastingRecipe> holder, IFocusGroup focuses) {
        ExtraBlastingRecipe recipe = holder.value();
        builder.addInputSlot(1, 5).setStandardSlotBackground().addItemStacks(stacks(recipe.input(), recipe.getInputCount()));
        builder.addInputSlot(21, 5).setStandardSlotBackground().addItemStacks(stacks(recipe.getExtraInput(), recipe.getExtraInputCount()));
        builder.addOutputSlot(79, 5).setOutputSlotBackground().add(recipe.getResult());
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<ExtraBlastingRecipe> holder, IFocusGroup focuses) {
        ExtraBlastingRecipe recipe = holder.value();
        builder.addAnimatedRecipeArrow(recipe.cookingTime()).setPosition(45, 5);
        builder.addAnimatedRecipeFlame(300).setPosition(2, 25);
        builder.addText(Component.translatable("gui.jei.category.smelting.experience", recipe.experience()), 60, 10).setPosition(40, 28, 60, 10, HorizontalAlignment.RIGHT,
                VerticalAlignment.TOP);
        builder.addTooltipArea(45, 5, 24, 17).setTooltip(Component.translatable("gui.jei.category.smelting.time.seconds", recipe.cookingTime() / 20f));
    }

    @Override
    public Identifier getIdentifier(RecipeHolder<ExtraBlastingRecipe> holder) {
        return holder.id().identifier();
    }

}
