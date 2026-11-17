package net.earlystage.data;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.display.RecipeDisplay;

public record CraftingRockRecipe(Identifier id, RecipeDisplay display) {

    public static final StreamCodec<RegistryFriendlyByteBuf, CraftingRockRecipe> STREAM_CODEC = StreamCodec.composite(Identifier.STREAM_CODEC, CraftingRockRecipe::id, RecipeDisplay.STREAM_CODEC,
            CraftingRockRecipe::display, CraftingRockRecipe::new);

}
