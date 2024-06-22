package net.earlystage.init;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;

public class WorldInit {

    public static Predicate<BiomeSelectionContext> rockPredicate = (context -> context.hasTag(BiomeTags.IS_FOREST) || context.hasTag(BiomeTags.IS_HILL) || context.hasTag(BiomeTags.IS_MOUNTAIN)
            || context.hasTag(BiomeTags.IS_RIVER) || context.hasTag(TagInit.ROCK_FEATURE_BIOMES));

    public static void init() {
        if (ConfigInit.CONFIG.generateRocks) {
            BiomeModifications.addFeature(rockPredicate, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("earlystage:rock")));
        }
        if (ConfigInit.CONFIG.generateFlint) {
            BiomeModifications.addFeature(rockPredicate, GenerationStep.Feature.TOP_LAYER_MODIFICATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of("earlystage:flint")));
        }
    }
}
