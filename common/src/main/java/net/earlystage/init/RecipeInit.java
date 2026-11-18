package net.earlystage.init;

import java.util.function.BiConsumer;

import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeInit {

    public static final RecipeType<ExtraBlastingRecipe> EXTRA_BLASTING = new RecipeType<>() {
        @Override
        public String toString() {
            return "blasting_extra";
        }
    };
    public static final RecipeSerializer<ExtraBlastingRecipe> EXTRA_BLASTING_SERIALIZER = new RecipeSerializer<>(ExtraBlastingRecipe.CODEC, ExtraBlastingRecipe.STREAM_CODEC);

    public static void registerTypes(BiConsumer<Identifier, RecipeType<?>> registrar) {
        registrar.accept(Identifier.withDefaultNamespace("blasting_extra"), EXTRA_BLASTING);
    }

    public static void registerSerializers(BiConsumer<Identifier, RecipeSerializer<?>> registrar) {
        registrar.accept(Identifier.withDefaultNamespace("blasting_extra"), EXTRA_BLASTING_SERIALIZER);
    }

}
