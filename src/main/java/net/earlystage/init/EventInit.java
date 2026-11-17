package net.earlystage.init;

import net.earlystage.data.DataLoader;
import net.earlystage.network.EarlyServerPacket;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ints.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.core.Holder;
import java.util.ArrayList;
import java.util.List;

public class EventInit {

    private static final List<Identifier> leavesBlockList = new ArrayList<>();

    public static void init() {
        BuiltInRegistries.BLOCK.forEach(EventInit::addLeavesBlock);
        RegistryEntryAddedCallback.event(BuiltInRegistries.BLOCK).register((rawId, id, block) -> addLeavesBlock(block));
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (leavesBlockList.contains(key.identifier()) && ConfigInit.CONFIG.extraStickDropChance > 0.0001f) {
                // Missing Silk Touch check here
                LootPool pool = LootPool.lootPool().add(LootItem.lootTableItem(Items.STICK).build()).setRolls(Holder.direct(new BinomialDistributionGenerator(ContextIntProviders.exactly(1), ContextFloatProviders.exactly(ConfigInit.CONFIG.extraStickDropChance))))
                        .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(registries.lookupOrThrow(Registries.ITEM), Items.SHEARS)).invert()).build();
                tableBuilder.pool(pool);
            }
        });

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

    private static void addLeavesBlock(Block block) {
        block.getLootTable().ifPresent(lootTable -> {
            if (lootTable.identifier().getPath().contains("leaves")) {
                leavesBlockList.add(lootTable.identifier());
            }
        });
    }

}
