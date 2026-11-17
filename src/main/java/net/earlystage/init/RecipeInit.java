package net.earlystage.init;

import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeInit {

    public static final RecipeType<ExtraBlastingRecipe> EXTRA_BLASTING = RecipeType.register("blasting_extra");
    public static final RecipeSerializer<ExtraBlastingRecipe> EXTRA_BLASTING_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.withDefaultNamespace("blasting_extra"),
            new RecipeSerializer<>(ExtraBlastingRecipe.CODEC, ExtraBlastingRecipe.STREAM_CODEC));

    public static void init() {
    }

}
