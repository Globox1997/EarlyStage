package net.earlystage.compat;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.earlystage.EarlyStageMain;
import net.earlystage.data.SieveDropTemplate;
import net.earlystage.init.BlockInit;
import net.earlystage.init.RecipeInit;
import net.earlystage.init.RenderInit;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class EarlyStageEmiPlugin implements EmiPlugin {

    public static final EmiStack CRAFTING_ROCK = EmiStack.of(BlockInit.CRAFTING_ROCK.asItem());
    public static final EmiRecipeCategory CRAFTING_ROCK_CATEGORY = new EmiRecipeCategory(EarlyStageMain.identifierOf("crafting_rock"), CRAFTING_ROCK,
            new EmiTexture(RenderInit.GUI_ICON_TEXTURES, 18, 0, 16, 16));

    public static final EmiStack SIEVE = EmiStack.of(BlockInit.SIEVE);
    public static final EmiRecipeCategory SIEVE_CATEGORY = new EmiRecipeCategory(EarlyStageMain.identifierOf("sieve"), SIEVE,
            new EmiTexture(RenderInit.SIEVE, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry emiRegistry) {
        RecipeManager manager = emiRegistry.getRecipeManager();
        for (RecipeEntry<ExtraBlastingRecipe> recipe : manager.listAllOfType(RecipeInit.EXTRA_BLASTING)) {
            emiRegistry.addRecipe(new ExtraBlastingEmiRecipe(recipe.value()));
        }
        emiRegistry.addCategory(CRAFTING_ROCK_CATEGORY);
        emiRegistry.addWorkstation(CRAFTING_ROCK_CATEGORY, CRAFTING_ROCK);

        for (ShapedRecipe recipe : getDefaultRockRecipes(manager)) {
            emiRegistry.addRecipe(new RockEmiRecipe(recipe));
        }

        emiRegistry.addCategory(SIEVE_CATEGORY);
        emiRegistry.addWorkstation(SIEVE_CATEGORY, SIEVE);

        for (SieveDropTemplate template : EarlyStageMain.SIEVE_DROP_TEMPLATES) {
            emiRegistry.addRecipe(new SieveEmiRecipe(template));
        }
    }

    private static List<ShapedRecipe> getDefaultRockRecipes(RecipeManager manager) {
        List<ShapedRecipe> list = new ArrayList<ShapedRecipe>();
        list.add((ShapedRecipe) manager.get(EarlyStageMain.identifierOf("flint_axe")).get().value());
        list.add((ShapedRecipe) manager.get(EarlyStageMain.identifierOf("flint_hoe")).get().value());
        list.add((ShapedRecipe) manager.get(EarlyStageMain.identifierOf("flint_pickaxe")).get().value());
        list.add((ShapedRecipe) manager.get(EarlyStageMain.identifierOf("flint_shovel")).get().value());
        list.add((ShapedRecipe) manager.get(EarlyStageMain.identifierOf("flint_sword")).get().value());
        return list;
    }

}
