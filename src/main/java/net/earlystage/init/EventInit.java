package net.earlystage.init;

import ht.treechop.api.TreeChopEvents;
import net.earlystage.data.DataLoader;
import net.earlystage.network.EarlyServerPacket;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import java.util.ArrayList;
import java.util.List;

public class EventInit {

    private static final List<ResourceLocation> leavesBlockList = new ArrayList<>();

    public static void init() {
        BuiltInRegistries.BLOCK.forEach((block) -> {
            if (block.getLootTable().location().getPath().contains("leaves")) {
                leavesBlockList.add(block.getLootTable().location());
            }
        });
        RegistryEntryAddedCallback.event(BuiltInRegistries.BLOCK).register((rawId, id, block) -> {
            if (block.getLootTable().location().getPath().contains("leaves")) {
                leavesBlockList.add(block.getLootTable().location());
            }
        });
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (leavesBlockList.contains(key.location()) && ConfigInit.CONFIG.extraStickDropChance > 0.0001f) {
                // Missing Silk Touch check here
                LootPool pool = LootPool.lootPool().with(LootItem.lootTableItem(Items.STICK).build()).setRolls(BinomialDistributionGenerator.binomial(1, ConfigInit.CONFIG.extraStickDropChance))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert()).build();
                tableBuilder.pool(pool);
            }
        });

        if (FabricLoader.getInstance().isModLoaded("treechop")) {
            TreeChopEvents.BEFORE_CHOP.register((world, player, pos, state, chopData) -> player == null || player.getMainHandItem().getItem() instanceof AxeItem);
        }

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            EarlyServerPacket.syncData(handler.getPlayer());
        });
        ServerLifecycleEvents.SERVER_STARTING.register(DataLoader::updateCraftingRockRecipeItems);
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            if (success) {
                DataLoader.updateCraftingRockRecipeItems(server);
                server.getPlayerList().getPlayers().forEach(EarlyServerPacket::syncData);
            }
        });
    }

}
