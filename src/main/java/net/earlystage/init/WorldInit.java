package net.earlystage.init;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WorldInit {

    public static Predicate<BiomeSelectionContext> rockPredicate = (context -> context.hasTag(BiomeTags.IS_FOREST) || context.hasTag(BiomeTags.IS_HILL) || context.hasTag(BiomeTags.IS_MOUNTAIN)
            || context.hasTag(BiomeTags.IS_RIVER) || context.hasTag(TagInit.ROCK_FEATURE_BIOMES));

    public static void init() {
        if (ConfigInit.CONFIG.generateRocks) {
            BiomeModifications.addFeature(rockPredicate, GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.parse("earlystage:rock")));
        }
        if (ConfigInit.CONFIG.generateFlint) {
            BiomeModifications.addFeature(rockPredicate, GenerationStep.Decoration.TOP_LAYER_MODIFICATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.parse("earlystage:flint")));
        }
    }
}
