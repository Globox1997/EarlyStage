package net.earlystage.init;

import net.earlystage.data.DataLoader;
import net.earlystage.network.EarlyServerPacket;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class EventInit {

    private static Set<Identifier> leavesBlockList;

    public static void onPlayerJoin(ServerPlayer player) {
        EarlyServerPacket.syncData(player);
    }

    public static void onServerStarting(MinecraftServer server) {
        DataLoader.updateCraftingRockRecipeItems(server);
    }

    public static void onDataPackReload(MinecraftServer server) {
        DataLoader.updateCraftingRockRecipeItems(server);
        server.getPlayerList().getPlayers().forEach(EarlyServerPacket::syncData);
    }

    public static Optional<LootPool> createExtraStickPool(ResourceKey<LootTable> key, HolderGetter.Provider registries) {
        if (getLeavesBlockList().contains(key.identifier()) && ConfigInit.CONFIG.extraStickDropChance > 0.0001f) {
            // Missing Silk Touch check here
            return Optional.of(LootPool.lootPool().add(LootItem.lootTableItem(Items.STICK))
                    .setRolls(Holder.direct(new BinomialDistributionGenerator(ContextIntProviders.exactly(1), ContextFloatProviders.exactly(ConfigInit.CONFIG.extraStickDropChance))))
                    .when(MatchTool.toolMatches(ItemPredicate.Builder.item().of(registries.lookupOrThrow(Registries.ITEM), Items.SHEARS)).invert()).build());
        }
        return Optional.empty();
    }

    private static synchronized Set<Identifier> getLeavesBlockList() {
        if (leavesBlockList == null) {
            Set<Identifier> lootTables = new HashSet<>();
            for (Block block : BuiltInRegistries.BLOCK) {
                block.getLootTable().ifPresent(lootTable -> {
                    if (lootTable.identifier().getPath().contains("leaves")) {
                        lootTables.add(lootTable.identifier());
                    }
                });
            }
            leavesBlockList = lootTables;
        }
        return leavesBlockList;
    }

}
