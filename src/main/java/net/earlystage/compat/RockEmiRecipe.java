package net.earlystage.compat;

import java.util.ArrayList;
import java.util.List;

import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.earlystage.EarlyStageMain;
import net.earlystage.init.ConfigInit;
import net.earlystage.init.RenderInit;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RockEmiRecipe extends EmiCraftingRecipe {

    public RockEmiRecipe(Identifier recipeId, CraftingRecipe recipe, ItemStack output) {
        super(createInputs(recipe), EmiStack.of(output), EarlyStageMain.identifierOf("/" + recipeId.getNamespace() + "/" + recipeId.getPath() + "_rock"), !(recipe instanceof ShapedRecipe));
    }

    private static List<EmiIngredient> createInputs(CraftingRecipe recipe) {
        List<EmiIngredient> inputs = new ArrayList<>();
        if (recipe instanceof ShapedRecipe shapedRecipe) {
            for (int i = 0; i < 9; i++) {
                int x = i % 3;
                int y = i / 3;
                if (x < shapedRecipe.getWidth() && y < shapedRecipe.getHeight()) {
                    inputs.add(EmiIngredient.of(shapedRecipe.getIngredients().get(y * shapedRecipe.getWidth() + x)));
                } else {
                    inputs.add(EmiStack.EMPTY);
                }
            }
        } else {
            recipe.getIngredients().forEach(ingredient -> inputs.add(EmiIngredient.of(ingredient)));
        }
        return inputs;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addText(Text.translatable("emi.crafting_rock.info", ConfigInit.CONFIG.craftRockCraftHits), 59, 5, 0xFFFFFF, true);
        widgets.addTexture(RenderInit.GUI_ICON_TEXTURES, 65, 38, 15, 12, 34, 0);
        super.addWidgets(widgets);
        widgets.addTooltipText(List.of(Text.translatable("emi.crafting_rock.tooltip")), 65, 38, 15, 12);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EarlyStageEmiPlugin.CRAFTING_ROCK_CATEGORY;
    }

}
