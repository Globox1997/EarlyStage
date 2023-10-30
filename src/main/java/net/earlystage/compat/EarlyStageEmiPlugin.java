package net.earlystage.compat;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import net.earlystage.init.RecipeInit;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.recipe.RecipeManager;

public class EarlyStageEmiPlugin implements EmiPlugin {

    @Override
    public void register(EmiRegistry emiRegistry) {
        RecipeManager manager = emiRegistry.getRecipeManager();
        for (ExtraBlastingRecipe recipe : manager.listAllOfType(RecipeInit.EXTRA_BLASTING)) {
            emiRegistry.addRecipe(new ExtraBlastingEmiRecipe(recipe));
        }
    }

}
