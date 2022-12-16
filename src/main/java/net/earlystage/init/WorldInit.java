package net.earlystage.init;

import java.util.function.Predicate;

import net.earlystage.world.RockFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.tag.BiomeTags;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.GenerationStep;

public class WorldInit {

    public static void init() {
        RockFeature.init();
        Predicate<BiomeSelectionContext> rockPredicate = (context -> context.hasTag(BiomeTags.IS_FOREST) || context.hasTag(BiomeTags.IS_HILL) || context.hasTag(BiomeTags.IS_MOUNTAIN)
                || context.hasTag(BiomeTags.IS_RIVER) || context.hasTag(TagInit.ROCK_FEATURE_BIOMES));
        if (ConfigInit.CONFIG.generateRocks)
            BiomeModifications.addFeature(rockPredicate, GenerationStep.Feature.TOP_LAYER_MODIFICATION, BuiltinRegistries.PLACED_FEATURE.getKey(RockFeature.ROCK_PLACED_FEATURE).get());
    }

}
