package net.earlystage.init;

import java.util.ArrayList;
import java.util.List;

import ht.treechop.api.TreeChopEvents;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EventInit {

    private static final List<Identifier> leavesBlockList = new ArrayList<>();

    public static void init() {
        Registries.BLOCK.forEach((block) -> {
            if (block.getLootTableKey().getValue().getPath().contains("leaves")) {
                leavesBlockList.add(block.getLootTableKey().getValue());
            }
        });
        RegistryEntryAddedCallback.event(Registries.BLOCK).register((rawId, id, block) -> {
            if (block.getLootTableKey().getValue().getPath().contains("leaves")) {
                leavesBlockList.add(block.getLootTableKey().getValue());
            }
        });

        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (leavesBlockList.contains(key.getValue()) && ConfigInit.CONFIG.extraStickDropChance > 0.0001f) {
                // Missing Silk Touch check here
                LootPool pool = LootPool.builder().with(ItemEntry.builder(Items.STICK).build()).rolls(BinomialLootNumberProvider.create(1, ConfigInit.CONFIG.extraStickDropChance))
                        .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS)).invert()).build();
                tableBuilder.pool(pool);
            }
        });

        if (FabricLoader.getInstance().isModLoaded("treechop")) {
            TreeChopEvents.BEFORE_CHOP.register((world, player, pos, state, chopData) -> {
                if (player != null && !(player.getMainHandStack().getItem() instanceof AxeItem)) {
                    return false;
                }
                return true;
            });
        }
    }

}
