package net.earlystage.compat;

import java.util.List;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ExtraBlastingEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;
    private final int cookTime;
    private final float experience;

    public ExtraBlastingEmiRecipe(ExtraBlastingRecipe recipe) {
        this.id = recipe.getId();
        this.input = List.of(EmiIngredient.of(recipe.getIngredients().get(0)), EmiIngredient.of(recipe.getIngredients().get(1)));
        this.output = List.of(EmiStack.of(recipe.getOutput(null)));
        this.cookTime = recipe.getCookTime();
        this.experience = recipe.getExperience();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return VanillaEmiRecipeCategories.BLASTING;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 100;
    }

    @Override
    public int getDisplayHeight() {
        return 39;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(43, 5, this.cookTime * 20);
        widgets.addText(Text.translatable("emi.cooking.experience", this.experience), 40, 28, 0xFFFFFF, true);
        widgets.addTexture(EmiTexture.EMPTY_FLAME, 1, 25);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 1, 25, 10000, false, true, true);
        widgets.addSlot(input.get(0), 0, 4);
        widgets.addSlot(input.get(1), 20, 4);
        widgets.addSlot(output.get(0), 74, 0).large(true).recipeContext(this);
    }

}
