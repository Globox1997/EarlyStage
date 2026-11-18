package net.earlystage.neoforge;

import net.earlystage.EarlyStageMain;
import net.earlystage.compat.EarlyStageDehydrationItems;
import net.earlystage.init.BlockInit;
import net.earlystage.init.CompatInit;
import net.earlystage.init.EventInit;
import net.earlystage.init.ItemInit;
import net.earlystage.init.LoaderInit;
import net.earlystage.init.RecipeInit;
import net.earlystage.network.packet.BeginnerDeathPacket;
import net.earlystage.network.packet.ConfigSyncPacket;
import net.earlystage.network.packet.CraftingRockItemsPacket;
import net.earlystage.network.packet.ExtraBlastingRecipesPacket;
import net.earlystage.network.packet.SieveDropPacket;
import net.earlystage.neoforge.world.RockBiomeModifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(EarlyStageMain.MOD_ID)
public class EarlyStageNeoForge {

    public EarlyStageNeoForge(IEventBus modBus) {
        EarlyStageMain.init();

        modBus.addListener(RegisterEvent.class, EarlyStageNeoForge::register);
        modBus.addListener(RegisterPayloadHandlersEvent.class, EarlyStageNeoForge::registerPayloads);
        modBus.addListener(AddPackFindersEvent.class, EarlyStageNeoForge::addPackFinders);

        NeoForge.EVENT_BUS.addListener(AddServerReloadListenersEvent.class, event -> LoaderInit.register(event::addListener));
        NeoForge.EVENT_BUS.addListener(LootTableLoadEvent.class,
                event -> EventInit.createExtraStickPool(event.getKey(), event.getRegistries()).ifPresent(pool -> event.getTable().addPool(pool)));
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedInEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer player) {
                EventInit.onPlayerJoin(player);
            }
        });
        NeoForge.EVENT_BUS.addListener(ServerStartingEvent.class, event -> EventInit.onServerStarting(event.getServer()));
        NeoForge.EVENT_BUS.addListener(OnDatapackSyncEvent.class, event -> {
            if (event.getPlayer() == null) {
                EventInit.onDataPackReload(event.getPlayerList().getServer());
            }
        });
    }

    private static void register(RegisterEvent event) {
        event.register(Registries.BLOCK, helper -> BlockInit.registerBlocks(helper::register));
        event.register(Registries.ITEM, helper -> {
            ItemInit.register(helper::register);
            BlockInit.registerItems(helper::register);
            CompatInit.registerItems(helper::register);
            if (ModList.get().isLoaded("dehydration")) {
                EarlyStageDehydrationItems.register(helper::register);
            }
        });
        event.register(Registries.CREATIVE_MODE_TAB, helper -> ItemInit.registerCreativeModeTab(helper::register));
        event.register(Registries.BLOCK_ENTITY_TYPE, helper -> BlockInit.registerBlockEntities(helper::register));
        event.register(Registries.RECIPE_TYPE, helper -> RecipeInit.registerTypes(helper::register));
        event.register(Registries.RECIPE_SERIALIZER, helper -> RecipeInit.registerSerializers(helper::register));
        event.register(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, helper -> helper.register(EarlyStageMain.identifierOf("rocks"), RockBiomeModifier.CODEC));
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1").optional();
        registrar.playToClient(BeginnerDeathPacket.PACKET_ID, BeginnerDeathPacket.PACKET_CODEC);
        registrar.playToClient(SieveDropPacket.PACKET_ID, SieveDropPacket.PACKET_CODEC);
        registrar.playToClient(CraftingRockItemsPacket.PACKET_ID, CraftingRockItemsPacket.PACKET_CODEC);
        registrar.playToClient(ConfigSyncPacket.PACKET_ID, ConfigSyncPacket.PACKET_CODEC);
        registrar.playToClient(ExtraBlastingRecipesPacket.PACKET_ID, ExtraBlastingRecipesPacket.PACKET_CODEC);
    }

    private static void addPackFinders(AddPackFindersEvent event) {
        CompatInit.COMPAT_PACKS.forEach((modId, pack) -> {
            if (ModList.get().isLoaded(modId)) {
                event.addPackFinders(EarlyStageMain.identifierOf("resourcepacks/" + pack), event.getPackType(),
                        Component.translatableWithFallback("pack." + EarlyStageMain.MOD_ID + "." + pack, pack), PackSource.BUILT_IN, true, Pack.Position.TOP);
            }
        });
    }

}
