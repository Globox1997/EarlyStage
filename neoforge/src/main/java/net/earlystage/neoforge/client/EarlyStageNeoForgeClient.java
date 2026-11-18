package net.earlystage.neoforge.client;

import me.shedaniel.autoconfig.AutoConfigClient;
import net.earlystage.EarlyStageMain;
import net.earlystage.block.render.CraftingRockBlockRenderer;
import net.earlystage.block.render.SieveBlockRenderer;
import net.earlystage.config.EarlyStageConfig;
import net.earlystage.init.BlockInit;
import net.earlystage.network.EarlyClientPacket;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.network.packet.ConfigSyncPacket;
import net.earlystage.network.packet.CraftingRockItemsPacket;
import net.earlystage.network.packet.ExtraBlastingRecipesPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = EarlyStageMain.MOD_ID, dist = Dist.CLIENT)
public class EarlyStageNeoForgeClient {

    public EarlyStageNeoForgeClient(IEventBus modBus, ModContainer container) {
        modBus.addListener(EntityRenderersEvent.RegisterRenderers.class, event -> {
            event.registerBlockEntityRenderer(BlockInit.SIEVE_ENTITY, SieveBlockRenderer::new);
            event.registerBlockEntityRenderer(BlockInit.CRAFTING_ROCK_ENTITY, CraftingRockBlockRenderer::new);
        });
        modBus.addListener(RegisterClientPayloadHandlersEvent.class, event -> {
            event.register(BeginnerDeathPacket.PACKET_ID, (payload, context) -> EarlyClientPacket.handleBeginnerDeath(payload));
            event.register(SieveDropPacket.PACKET_ID, (payload, context) -> EarlyClientPacket.handleSieveDrops(payload));
            event.register(CraftingRockItemsPacket.PACKET_ID, (payload, context) -> EarlyClientPacket.handleCraftingRockItems(payload));
            event.register(ExtraBlastingRecipesPacket.PACKET_ID, (payload, context) -> EarlyClientPacket.handleExtraBlastingRecipes(payload));
            event.register(ConfigSyncPacket.PACKET_ID, (payload, context) -> EarlyClientPacket.handleConfigSync(payload));
        });
        NeoForge.EVENT_BUS.addListener(ClientPlayerNetworkEvent.LoggingOut.class, event -> EarlyClientPacket.onDisconnect());

        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> AutoConfigClient.getConfigScreen(EarlyStageConfig.class, parent).get());
    }

}
