package net.earlystage.init;

import java.util.ArrayList;
import java.util.List;

import net.earlystage.mixin.access.BlockLootTableGeneratorAccessor;
import net.earlystage.world.Features;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.BinomialLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.gen.GenerationStep;

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

        // RegistryEntryAddedCallback.event(Registry.BIOME_KEY).register((rawId, id, biome) -> {

        // if (ConfigInit.CONFIG.generateRocks) {
        // System.out.println(rawId + " : " + id + " : ");

        // // BiomeModifications.addFeature(WorldInit.rockPredicate, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(Features.ROCK_PLACED_FEATURE).get());
        // }
        // // if (ConfigInit.CONFIG.generateFlint)
        // // BiomeModifications.addFeature(rockPredicate, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(Features.FLINT_PLACED_FEATURE).get());
        // // if (block.getLootTableId().getPath().contains("leaves"))
        // // leavesBlockList.add(block.getLootTableId());
        // });

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, supplier, setter) -> {
            if (leavesBlockList.contains(id)) {
                LootPool pool = LootPool.builder().with(ItemEntry.builder(Items.STICK).build()).rolls(BinomialLootNumberProvider.create(2, 0.05F))
                        .conditionally(BlockLootTableGeneratorAccessor.getWithoutSilkTouchNorShears()).build();
                supplier.pool(pool);
            }
        });

    }

}
