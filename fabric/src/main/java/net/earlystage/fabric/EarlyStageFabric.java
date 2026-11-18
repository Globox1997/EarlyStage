package net.earlystage.fabric;

import java.util.function.BiConsumer;

import net.earlystage.EarlyStageMain;
import net.earlystage.init.BlockInit;
import net.earlystage.init.CompatInit;
import net.earlystage.init.EventInit;
import net.earlystage.init.ItemInit;
import net.earlystage.init.LoaderInit;
import net.earlystage.init.RecipeInit;
import net.earlystage.init.WorldInit;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.network.packet.ConfigSyncPacket;
import net.earlystage.network.packet.CraftingRockItemsPacket;
import net.earlystage.network.packet.ExtraBlastingRecipesPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;

public class EarlyStageFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        EarlyStageMain.init();

        BlockInit.registerBlocks((id, block) -> Registry.register(BuiltInRegistries.BLOCK, id, block));
        BiConsumer<Identifier, Item> itemRegistrar = (id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item);
        ItemInit.register(itemRegistrar);
        BlockInit.registerItems(itemRegistrar);
        CompatInit.registerItems(itemRegistrar);
        ItemInit.registerCreativeModeTab((id, tab) -> Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id, tab));
        BlockInit.registerBlockEntities((id, type) -> Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, type));
        RecipeInit.registerTypes((id, type) -> Registry.register(BuiltInRegistries.RECIPE_TYPE, id, type));
        RecipeInit.registerSerializers((id, serializer) -> Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializer));

        ModContainer container = FabricLoader.getInstance().getModContainer(EarlyStageMain.MOD_ID).orElseThrow();
        CompatInit.COMPAT_PACKS.forEach((modId, pack) -> {
            if (FabricLoader.getInstance().isModLoaded(modId)) {
                ResourceLoader.registerBuiltinPack(EarlyStageMain.identifierOf(pack), container, PackActivationType.DEFAULT_ENABLED);
            }
        });
        LoaderInit.register((id, listener) -> ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(id, listener));

        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> EventInit.createExtraStickPool(key, registries).ifPresent(tableBuilder::pool));
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> EventInit.onPlayerJoin(handler.getPlayer()));
        ServerLifecycleEvents.SERVER_STARTING.register(EventInit::onServerStarting);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            if (success) {
                EventInit.onDataPackReload(server);
            }
        });

        WorldInit.getEnabledFeatures().forEach(feature -> BiomeModifications.addFeature(context -> WorldInit.isRockBiome(context::hasTag), WorldInit.STEP, feature));

        PayloadTypeRegistry.clientboundPlay().register(BeginnerDeathPacket.PACKET_ID, BeginnerDeathPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SieveDropPacket.PACKET_ID, SieveDropPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(CraftingRockItemsPacket.PACKET_ID, CraftingRockItemsPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ConfigSyncPacket.PACKET_ID, ConfigSyncPacket.PACKET_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ExtraBlastingRecipesPacket.PACKET_ID, ExtraBlastingRecipesPacket.PACKET_CODEC);
    }

}
