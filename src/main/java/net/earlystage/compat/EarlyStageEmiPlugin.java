package net.earlystage.compat;

import java.util.ArrayList;
import java.util.List;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.earlystage.init.BlockInit;
import net.earlystage.init.RecipeInit;
import net.earlystage.init.RenderInit;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;

public class EarlyStageEmiPlugin implements EmiPlugin {

    public static final EmiStack CRAFTING_ROCK = EmiStack.of(BlockInit.CRAFTING_ROCK.asItem());
    public static final EmiRecipeCategory CRAFTING_ROCK_CATEGORY = new EmiRecipeCategory(new Identifier("earlystage", "crafting_rock"), CRAFTING_ROCK,
            new EmiTexture(RenderInit.GUI_ICON_TEXTURES, 18, 0, 16, 16));

    @Override
    public void register(EmiRegistry emiRegistry) {
        RecipeManager manager = emiRegistry.getRecipeManager();
        for (ExtraBlastingRecipe recipe : manager.listAllOfType(RecipeInit.EXTRA_BLASTING)) {
            emiRegistry.addRecipe(new ExtraBlastingEmiRecipe(recipe));
        }
        emiRegistry.addCategory(CRAFTING_ROCK_CATEGORY);
        emiRegistry.addWorkstation(CRAFTING_ROCK_CATEGORY, CRAFTING_ROCK);

        for (ShapedRecipe recipe : getDefaultRockRecipes(manager)) {
            emiRegistry.addRecipe(new RockEmiRecipe(recipe));
        }
    }

    private static final List<ShapedRecipe> getDefaultRockRecipes(RecipeManager manager) {
        List<ShapedRecipe> list = new ArrayList<ShapedRecipe>();
        list.add((ShapedRecipe) manager.get(new Identifier("earlystage", "flint_axe")).get());
        list.add((ShapedRecipe) manager.get(new Identifier("earlystage", "flint_hoe")).get());
        list.add((ShapedRecipe) manager.get(new Identifier("earlystage", "flint_pickaxe")).get());
        list.add((ShapedRecipe) manager.get(new Identifier("earlystage", "flint_shovel")).get());
        list.add((ShapedRecipe) manager.get(new Identifier("earlystage", "flint_sword")).get());
        return list;
    }

}
