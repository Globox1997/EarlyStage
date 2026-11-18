package net.earlystage.network;

import net.earlystage.EarlyStageMain;
import net.earlystage.init.ConfigInit;
import net.earlystage.network.packet.ConfigSyncPacket;
import net.earlystage.network.packet.CraftingRockItemsPacket;
import net.earlystage.network.packet.ExtraBlastingRecipesPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.earlystage.platform.Services;
import net.earlystage.platform.services.INetworkHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarlyServerPacket {

    public static void syncData(ServerPlayer player) {
        INetworkHelper network = Services.network();
        if (network.canSend(player, SieveDropPacket.PACKET_ID)) {
            network.sendToPlayer(player, new SieveDropPacket(List.copyOf(EarlyStageMain.SIEVE_DROP_TEMPLATES)));
        }
        if (network.canSend(player, CraftingRockItemsPacket.PACKET_ID)) {
            Map<Item, List<Item>> recipeItems = new HashMap<>();
            EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.forEach((output, inputs) -> recipeItems.put(output, List.copyOf(inputs)));
            network.sendToPlayer(player, new CraftingRockItemsPacket(recipeItems, EarlyStageMain.CRAFTING_ROCK_RECIPES));
        }
        if (network.canSend(player, ExtraBlastingRecipesPacket.PACKET_ID)) {
            network.sendToPlayer(player, new ExtraBlastingRecipesPacket(EarlyStageMain.EXTRA_BLASTING_RECIPES));
        }
        if (network.canSend(player, ConfigSyncPacket.PACKET_ID)) {
            network.sendToPlayer(player, new ConfigSyncPacket(ConfigInit.CONFIG.blastFurnaceExtraSlot));
        }
    }

}
