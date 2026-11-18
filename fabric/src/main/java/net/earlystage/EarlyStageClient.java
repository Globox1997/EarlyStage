package net.earlystage;

import net.earlystage.block.render.CraftingRockBlockRenderer;
import net.earlystage.block.render.SieveBlockRenderer;
import net.earlystage.init.BlockInit;
import net.earlystage.network.EarlyClientPacket;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.network.packet.ConfigSyncPacket;
import net.earlystage.network.packet.CraftingRockItemsPacket;
import net.earlystage.network.packet.ExtraBlastingRecipesPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class EarlyStageClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(BlockInit.SIEVE_ENTITY, SieveBlockRenderer::new);
        BlockEntityRendererRegistry.register(BlockInit.CRAFTING_ROCK_ENTITY, CraftingRockBlockRenderer::new);

        ClientPlayNetworking.registerGlobalReceiver(BeginnerDeathPacket.PACKET_ID, (payload, context) -> context.client().execute(() -> EarlyClientPacket.handleBeginnerDeath(payload)));
        ClientPlayNetworking.registerGlobalReceiver(SieveDropPacket.PACKET_ID, (payload, context) -> context.client().execute(() -> EarlyClientPacket.handleSieveDrops(payload)));
        ClientPlayNetworking.registerGlobalReceiver(CraftingRockItemsPacket.PACKET_ID,
                (payload, context) -> context.client().execute(() -> EarlyClientPacket.handleCraftingRockItems(payload)));
        ClientPlayNetworking.registerGlobalReceiver(ExtraBlastingRecipesPacket.PACKET_ID,
                (payload, context) -> context.client().execute(() -> EarlyClientPacket.handleExtraBlastingRecipes(payload)));
        ClientPlayNetworking.registerGlobalReceiver(ConfigSyncPacket.PACKET_ID, (payload, context) -> context.client().execute(() -> EarlyClientPacket.handleConfigSync(payload)));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> EarlyClientPacket.onDisconnect());
    }

}
