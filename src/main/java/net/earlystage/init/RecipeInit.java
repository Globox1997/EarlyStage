package net.earlystage.init;

import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;

public class RecipeInit {

    public static final RecipeType<ExtraBlastingRecipe> EXTRA_BLASTING = RecipeType.register("blasting_extra");
    public static final RecipeSerializer<ExtraBlastingRecipe> EXTRA_BLASTING_SERIALIZER = RecipeSerializer.register("blasting_extra", new ExtraBlastingRecipe.Serializer());

    public static void init() {
    }

}
