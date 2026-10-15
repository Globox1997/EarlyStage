package net.earlystage.network.packet;

import net.earlystage.EarlyStageMain;
import net.minecraft.item.Item;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CraftingRockItemsPacket(Map<Item, List<Item>> recipeItems) implements CustomPayload {

    public static final Id<CraftingRockItemsPacket> PACKET_ID = new Id<>(EarlyStageMain.identifierOf("crafting_rock_items_packet"));

    public static final PacketCodec<RegistryByteBuf, CraftingRockItemsPacket> PACKET_CODEC = PacketCodec.of(CraftingRockItemsPacket::write, CraftingRockItemsPacket::read);

    private static void write(CraftingRockItemsPacket payload, RegistryByteBuf buf) {
        buf.writeInt(payload.recipeItems().size());
        for (Map.Entry<Item, List<Item>> entry : payload.recipeItems().entrySet()) {
            buf.writeInt(Registries.ITEM.getRawId(entry.getKey()));
            buf.writeInt(entry.getValue().size());
            for (Item item : entry.getValue()) {
                buf.writeInt(Registries.ITEM.getRawId(item));
            }
        }
    }

    private static CraftingRockItemsPacket read(RegistryByteBuf buf) {
        int outputCount = buf.readInt();
        Map<Item, List<Item>> recipeItems = new HashMap<>();
        for (int i = 0; i < outputCount; i++) {
            Item output = Registries.ITEM.get(buf.readInt());
            int inputCount = buf.readInt();
            List<Item> inputs = new ArrayList<>();
            for (int u = 0; u < inputCount; u++) {
                inputs.add(Registries.ITEM.get(buf.readInt()));
            }
            recipeItems.put(output, inputs);
        }
        return new CraftingRockItemsPacket(recipeItems);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
