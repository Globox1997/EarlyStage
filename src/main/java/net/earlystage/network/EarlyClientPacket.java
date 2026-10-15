package net.earlystage.network;

import net.earlystage.EarlyStageMain;
import net.earlystage.init.ConfigInit;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.network.packet.ConfigSyncPacket;
import net.earlystage.network.packet.CraftingRockItemsPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.stat.Stats;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class EarlyClientPacket {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(BeginnerDeathPacket.PACKET_ID, (payload, context) -> {
            int entityId = payload.entityId();
            int deathCount = payload.deathCount();

            if (context.client().world.getEntityById(entityId) instanceof ClientPlayerEntity clientPlayerEntity) {
                clientPlayerEntity.getStatHandler().setStat(clientPlayerEntity, Stats.CUSTOM.getOrCreateStat(Stats.DEATHS), deathCount);
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(SieveDropPacket.PACKET_ID, (payload, context) -> {
            if (!context.client().isIntegratedServerRunning()) {
                EarlyStageMain.SIEVE_DROP_TEMPLATES.clear();
                EarlyStageMain.SIEVE_DROP_TEMPLATES.addAll(payload.templates());
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(CraftingRockItemsPacket.PACKET_ID, (payload, context) -> {
            if (!context.client().isIntegratedServerRunning()) {
                EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.clear();
                payload.recipeItems().forEach((output, inputs) -> EarlyStageMain.CRAFTING_ROCK_RECIPE_ITEMS.put(output, new ArrayList<>(inputs)));
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(ConfigSyncPacket.PACKET_ID, (payload, context) -> {
            if (!context.client().isIntegratedServerRunning()) {
                ConfigInit.SYNCED_BLAST_FURNACE_EXTRA_SLOT = payload.blastFurnaceExtraSlot();
            }
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ConfigInit.SYNCED_BLAST_FURNACE_EXTRA_SLOT = null;
        });
    }

}
