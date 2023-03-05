package net.earlystage.init;

import java.util.ArrayList;
import java.util.List;

import net.earlystage.mixin.access.BlockLootTableGeneratorAccessor;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EventInit {

    private static final List<Identifier> leavesBlockList = new ArrayList<>();

    public static void init() {
        Registry.BLOCK.forEach((block) -> {
            if (block.getLootTableId().getPath().contains("leaves"))
                leavesBlockList.add(block.getLootTableId());
        });
        RegistryEntryAddedCallback.event(Registry.BLOCK).register((rawId, id, block) -> {
            if (block.getLootTableId().getPath().contains("leaves"))
                leavesBlockList.add(block.getLootTableId());
        });

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (leavesBlockList.contains(id) && ConfigInit.CONFIG.extraStickDropChance > 0.0001f) {
                LootPool pool = LootPool.builder().with(ItemEntry.builder(Items.STICK).build()).rolls(BinomialLootNumberProvider.create(1, ConfigInit.CONFIG.extraStickDropChance))
                        .conditionally(BlockLootTableGeneratorAccessor.getWithoutSilkTouchNorShears()).build();
                supplier.pool(pool);
            }
        });

    }

}
