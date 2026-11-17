package net.earlystage.network.packet;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;

import net.earlystage.EarlyStageMain;
import net.earlystage.misc.ExtraBlastingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public record ExtraBlastingRecipesPacket(List<RecipeHolder<ExtraBlastingRecipe>> recipes) implements CustomPacketPayload {

    public static final Type<ExtraBlastingRecipesPacket> PACKET_ID = new Type<>(EarlyStageMain.identifierOf("extra_blasting_recipes_packet"));

    private static final StreamCodec<ByteBuf, ResourceKey<Recipe<?>>> KEY_CODEC = ResourceKey.streamCodec(Registries.RECIPE);

    public static final StreamCodec<RegistryFriendlyByteBuf, ExtraBlastingRecipesPacket> PACKET_CODEC = StreamCodec.ofMember(ExtraBlastingRecipesPacket::write, ExtraBlastingRecipesPacket::read);

    private static void write(ExtraBlastingRecipesPacket payload, RegistryFriendlyByteBuf buf) {
        buf.writeInt(payload.recipes().size());
        for (RecipeHolder<ExtraBlastingRecipe> recipe : payload.recipes()) {
            KEY_CODEC.encode(buf, recipe.id());
            ExtraBlastingRecipe.STREAM_CODEC.encode(buf, recipe.value());
        }
    }

    private static ExtraBlastingRecipesPacket read(RegistryFriendlyByteBuf buf) {
        int recipeCount = buf.readInt();
        List<RecipeHolder<ExtraBlastingRecipe>> recipes = new ArrayList<>();
        for (int i = 0; i < recipeCount; i++) {
            ResourceKey<Recipe<?>> id = KEY_CODEC.decode(buf);
            recipes.add(new RecipeHolder<>(id, ExtraBlastingRecipe.STREAM_CODEC.decode(buf)));
        }
        return new ExtraBlastingRecipesPacket(recipes);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}
