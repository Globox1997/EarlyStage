package net.earlystage.network;

import net.earlystage.EarlyStageMain;
import net.earlystage.init.ConfigInit;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.network.packet.ConfigSyncPacket;
import net.earlystage.network.packet.CraftingRockItemsPacket;
import net.earlystage.network.packet.ExtraBlastingRecipesPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarlyServerPacket {

    public static void init() {
        PayloadTypeRegistry.clientboundPlay().register(BeginnerDeathPacket.PACKET_ID, BeginnerDeathPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SieveDropPacket.PACKET_ID, SieveDropPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(CraftingRockItemsPacket.PACKET_ID, CraftingRockItemsPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ConfigSyncPacket.PACKET_ID, ConfigSyncPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ExtraBlastingRecipesPacket.PACKET_ID, ExtraBlastingRecipesPacket.PACKET_CODEC);
    }

    public static void syncData(ServerPlayer player) {
        if (ServerPlayNetworking.canSend(player, SieveDropPacket.PACKET_ID)) {
            ServerPlayNetworking.send(player, new SieveDropPacket(List.copyOf(EarlyStageMain.SIEVE_DROP_TEMPLATES)));
        }
        if (ServerPlayNetworking.canSend(player, CraftingRockItemsPacket.PACKET_ID)) {
            Map<Item, List<Item>> recipeItems = new HashMap<>();
            EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.forEach((output, inputs) -> recipeItems.put(output, List.copyOf(inputs)));
            ServerPlayNetworking.send(player, new CraftingRockItemsPacket(recipeItems, EarlyStageMain.CRAFTING_ROCK_RECIPES));
        }
        if (ServerPlayNetworking.canSend(player, ExtraBlastingRecipesPacket.PACKET_ID)) {
            ServerPlayNetworking.send(player, new ExtraBlastingRecipesPacket(EarlyStageMain.EXTRA_BLASTING_RECIPES));
        }
        if (ServerPlayNetworking.canSend(player, ConfigSyncPacket.PACKET_ID)) {
            ServerPlayNetworking.send(player, new ConfigSyncPacket(ConfigInit.CONFIG.blastFurnaceExtraSlot));
        }
    }

}
