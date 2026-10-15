package net.earlystage.network;

import net.earlystage.EarlyStageMain;
import net.earlystage.init.ConfigInit;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.network.packet.ConfigSyncPacket;
import net.earlystage.network.packet.CraftingRockItemsPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarlyServerPacket {

    public static void init() {
        PayloadTypeRegistry.playS2C().register(BeginnerDeathPacket.PACKET_ID, BeginnerDeathPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(SieveDropPacket.PACKET_ID, SieveDropPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(CraftingRockItemsPacket.PACKET_ID, CraftingRockItemsPacket.PACKET_CODEC);
        PayloadTypeRegistry.playS2C().register(ConfigSyncPacket.PACKET_ID, ConfigSyncPacket.PACKET_CODEC);
    }

    public static void syncData(ServerPlayerEntity player) {
        if (ServerPlayNetworking.canSend(player, SieveDropPacket.PACKET_ID)) {
            ServerPlayNetworking.send(player, new SieveDropPacket(List.copyOf(EarlyStageMain.SIEVE_DROP_TEMPLATES)));
        }
        if (ServerPlayNetworking.canSend(player, CraftingRockItemsPacket.PACKET_ID)) {
            Map<Item, List<Item>> recipeItems = new HashMap<>();
            EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.forEach((output, inputs) -> recipeItems.put(output, List.copyOf(inputs)));
            ServerPlayNetworking.send(player, new CraftingRockItemsPacket(recipeItems));
        }
        if (ServerPlayNetworking.canSend(player, ConfigSyncPacket.PACKET_ID)) {
            ServerPlayNetworking.send(player, new ConfigSyncPacket(ConfigInit.CONFIG.blastFurnaceExtraSlot));
        }
    }

}
