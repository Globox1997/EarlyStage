package net.earlystage.network.packet;

import net.earlystage.EarlyStageMain;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CraftingRockItemsPacket(Map<Item, List<Item>> recipeItems, List<ResourceLocation> recipeIds) implements CustomPacketPayload {

    public static final Type<CraftingRockItemsPacket> PACKET_ID = new Type<>(EarlyStageMain.identifierOf("crafting_rock_items_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CraftingRockItemsPacket> PACKET_CODEC = StreamCodec.ofMember(CraftingRockItemsPacket::write, CraftingRockItemsPacket::read);

    private static void write(CraftingRockItemsPacket payload, RegistryFriendlyByteBuf buf) {
        buf.writeInt(payload.recipeItems().size());
        for (Map.Entry<Item, List<Item>> entry : payload.recipeItems().entrySet()) {
            buf.writeInt(BuiltInRegistries.ITEM.getId(entry.getKey()));
            buf.writeInt(entry.getValue().size());
            for (Item item : entry.getValue()) {
                buf.writeInt(BuiltInRegistries.ITEM.getId(item));
            }
        }
        buf.writeInt(payload.recipeIds().size());
        for (ResourceLocation recipeId : payload.recipeIds()) {
            buf.writeResourceLocation(recipeId);
        }
    }

    private static CraftingRockItemsPacket read(RegistryFriendlyByteBuf buf) {
        int outputCount = buf.readInt();
        Map<Item, List<Item>> recipeItems = new HashMap<>();
        for (int i = 0; i < outputCount; i++) {
            Item output = BuiltInRegistries.ITEM.byId(buf.readInt());
            int inputCount = buf.readInt();
            List<Item> inputs = new ArrayList<>();
            for (int u = 0; u < inputCount; u++) {
                inputs.add(BuiltInRegistries.ITEM.byId(buf.readInt()));
            }
            recipeItems.put(output, inputs);
        }
        int recipeIdCount = buf.readInt();
        List<ResourceLocation> recipeIds = new ArrayList<>();
        for (int i = 0; i < recipeIdCount; i++) {
            recipeIds.add(buf.readResourceLocation());
        }
        return new CraftingRockItemsPacket(recipeItems, recipeIds);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_ID;
    }
}
